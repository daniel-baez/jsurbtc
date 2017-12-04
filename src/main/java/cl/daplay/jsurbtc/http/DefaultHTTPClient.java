package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.HTTPClient;
import cl.daplay.jsurbtc.Signer;
import cl.daplay.jsurbtc.Utils;

import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.Optional;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

public final class DefaultHTTPClient implements HTTPClient {

    private final static Logger LOGGER = Logger.getLogger(DefaultHTTPClient.class.getName());

    private static final String BASE_PATH = "https://www.surbtc.com";

    private final Proxy proxy;
    private final String secret;
    private final String key;
    private final LongSupplier nonceSupplier;

    public DefaultHTTPClient(final Proxy proxy, final String secret, final String key, final LongSupplier nonceSupplier) {
        this.proxy = proxy;
        this.secret = secret;
        this.key = key;
        this.nonceSupplier = nonceSupplier;
    }

    @Override
    public <T> T get(final String path, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return doRequest(path, "GET", null, responseHandler);
    }

    @Override
    public <T> T put(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return doRequest(path, "PUT", jsonBody, responseHandler);
    }

    @Override
    public <T> T post(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return doRequest(path, "POST", jsonBody, responseHandler);
    }

    private <T> T doRequest(final String path, final String method, final Supplier<String> bodySupplier, final HTTPResponseHandler<T> responseHandler) throws Exception {
        final URL url = new URL(BASE_PATH + path);
        final HttpURLConnection con = (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));

        final String requestBody = Optional.ofNullable(bodySupplier)
                .map(Supplier::get)
                .orElse("");

        final long nonce = nonceSupplier.getAsLong();
        final String signature = new Signer(this.secret).sign(requestBody, method, path, nonce);

        // headers
        con.setRequestMethod(method);

        con.setRequestProperty("X-SBTC-APIKEY", key);
        con.setRequestProperty("X-SBTC-NONCE", Long.toString(nonce, 10));
        con.setRequestProperty("X-SBTC-SIGNATURE", signature);

        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("accept", "application/json");

        // Accept-Encoding: gzip,deflate
        con.setRequestProperty("User-Agent", "JSurbtcImpl/2.0.0");
        con.setRequestProperty("Accept-Encoding", "gzip,deflate");

        Reader reader = null;

        try {
            // writes body if any
            if (!requestBody.isEmpty()) {
                con.setDoOutput(true);
                final OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
                writer.write(requestBody);
                writer.close();
            }

            // handles input
            if ("gzip".equals(con.getContentEncoding())) {
                reader = new InputStreamReader(new GZIPInputStream(con.getInputStream()));
            } else {
                reader = new InputStreamReader(con.getInputStream());
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

}
