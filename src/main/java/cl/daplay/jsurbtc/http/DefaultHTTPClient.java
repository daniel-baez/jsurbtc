package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.HTTPClient;
import cl.daplay.jsurbtc.JSurbtcException;
import cl.daplay.jsurbtc.Signer;
import cl.daplay.jsurbtc.Utils;

import java.io.*;
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

    @FunctionalInterface
    interface ThrowingSupplier<T> {

        T get() throws Exception;

    }

    private <T> T retry(final int limit, final ThrowingSupplier<T> supplier) throws Exception {
        int retries = 0;
        Exception lastEx = null;

        while (true) {
            if (retries > limit) {
                if (lastEx != null) {
                    throw lastEx;
                }
            }

            try {
                return supplier.get();
            } catch (Exception ex) {
                lastEx = ex;
                retries = retries + 1;
            }
        }

    }

    private <T> T doRequest(final String path,
                            final String method,
                            final Supplier<String> bodySupplier,
                            final HTTPResponseHandler<T> responseHandler) throws Exception {
        // TODO: this should be configurable
        // TODO: move retrying to another implementation of HTTPClient
        return retry(5, () -> {
            return __doRequest(path, method, bodySupplier, responseHandler);
        });
    }

    private <T> T __doRequest(final String path,
                            final String method,
                            final Supplier<String> bodySupplier,
                            final HTTPResponseHandler<T> responseHandler) throws Exception {
        final URL url = new URL(BASE_PATH + path);
        final HttpURLConnection con = (HttpURLConnection) (proxy == null ? url.openConnection() : url.openConnection(proxy));

        final String requestBody = Optional.ofNullable(bodySupplier)
                .map(Supplier::get)
                .orElse("");

        final long nonce = nonceSupplier.getAsLong();
        final String signature = new Signer(this.secret).sign(requestBody, method, path, nonce);

        // TODO: these for endpoint doesn't need signature
        final boolean publicEndpoint = path.endsWith("ticker") || path.endsWith("order_book") || path.endsWith("trades") || path.endsWith("markets");

        if (!publicEndpoint) {
            // headers
            con.setRequestMethod(method);

            con.setRequestProperty("X-SBTC-APIKEY", key);
            con.setRequestProperty("X-SBTC-NONCE", Long.toString(nonce, 10));
            con.setRequestProperty("X-SBTC-SIGNATURE", signature);

            if (!requestBody.isEmpty()) {
                con.setRequestProperty("Content-Type", "application/json");
            }
        }

        con.setRequestProperty("accept", "application/json");
        con.setRequestProperty("Accept-Encoding", "gzip,deflate");
        con.setRequestProperty("User-Agent", "JSurbtc/2.0.0");

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
