package cl.daplay.jsurbtc.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Optional;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import cl.daplay.jsurbtc.*;

public final class DefaultHTTPClient implements HTTPClient {

    private static final String BASE_PATH = "https://www.surbtc.com";

    private final Proxy proxy;
    private final String key;
    private final LongSupplier nonceSupplier;
    private final String version;

    public DefaultHTTPClient(final Proxy proxy,
                             final String key,
                             final LongSupplier nonceSupplier,
                             final String version) {
        this.proxy = proxy;
        this.key = key;
        this.nonceSupplier = nonceSupplier;
        this.version = version;
    }

    @Override
    public <T> T get(final String path, final Signer signer, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return doRequest(path, signer, "GET", null, responseHandler);

    }

    @Override
    public <T> T put(final String path, final Signer signer, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return doRequest(path, signer, "PUT", jsonBody, responseHandler);

    }

    @Override
    public <T> T post(final String path, final Signer signer, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return doRequest(path, signer, "POST", jsonBody, responseHandler);

    }

    private <T> T doRequest(final String path,
                            final Signer signer,
                            final String method,
                            final Supplier<String> bodySupplier,
                            final HTTPResponseHandler<T> responseHandler) throws Exception {
        final URL url = new URL(BASE_PATH + path);
        final HttpURLConnection con = (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));

        final String requestBody = Optional.ofNullable(bodySupplier)
                .map(Supplier::get)
                .orElse("");

        final long nonce = nonceSupplier.getAsLong();
        final String signature = signer.sign(requestBody, method, path, nonce);

        if (!signature.isEmpty()) {
            // headers
            con.setRequestMethod(method);

            if (key == null || key.isEmpty()) {
                throw new JSurbtcException("API Key is missing.");
            }

            con.setRequestProperty("X-SBTC-APIKEY", key);
            con.setRequestProperty("X-SBTC-NONCE", Long.toString(nonce, 10));
            con.setRequestProperty("X-SBTC-SIGNATURE", signature);

            if (!requestBody.isEmpty()) {
                con.setRequestProperty("Content-Type", "application/json");
            }
        }

        con.setRequestProperty("accept", "application/json");
        con.setRequestProperty("Accept-Encoding", "gzip,deflate");
        con.setRequestProperty("User-Agent", "JSurbtc/" + version);

        Reader reader = null;

        try {
            // writes body if any
            if (!requestBody.isEmpty()) {
                con.setDoOutput(true);
                final OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(requestBody);
                writer.close();
            }

            final InputStream pipe = pipe(con);

            // handles input
            if (null != pipe) {
                if ("gzip".equals(con.getContentEncoding())) {
                    reader = new InputStreamReader(new GZIPInputStream(pipe(con)));
                } else {
                    reader = new InputStreamReader(pipe(con));
                }
            } else {
                reader = new StringReader("");
            }

            final int statusCode = con.getResponseCode();
            final String body = Utils.convertStreamToString(reader);

            return responseHandler.handle(statusCode, body);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
    }

    private InputStream pipe(HttpURLConnection con) {
        try {
            return con.getInputStream();
        } catch (IOException e) {
            return con.getErrorStream();
        }
    }

}
