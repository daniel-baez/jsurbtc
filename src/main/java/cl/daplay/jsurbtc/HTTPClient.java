package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.http.SurbtcHttpRequestInterceptor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.UnsupportedEncodingException;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.logging.Logger;

final class HTTPClient {

    private static final String BASE_PATH = "https://www.surbtc.com/";
    private final static Logger LOGGER = Logger.getLogger(HTTPClient.class.getName());

    static HTTPClient newInstance(final String key,
                                  final String secret,
                                  final LongSupplier nonceSupplier,
                                  final HttpHost proxy) {
        final CloseableHttpClient build = HttpClients.custom()
                .addInterceptorFirst(new SurbtcHttpRequestInterceptor(key, secret, nonceSupplier))
                .setProxy(proxy)
                .build();

        return new HTTPClient(build);
    }

    @FunctionalInterface
    interface HTTPResponseHandler<T> {

        T handle(final int statusCode, final String responseBody) throws Exception;

    }

    private final CloseableHttpClient httpClient;

    HTTPClient(final CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    <T> T get(final String path, final HTTPResponseHandler<T> responseMapper) throws Exception {
        final HttpGet get = new HttpGet(BASE_PATH + path);
        return executeRequest(get, responseMapper);
    }

    <T> T put(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        final HttpPut put = new HttpPut(BASE_PATH + path);
        return executeRequest(put, jsonBody, responseHandler);
    }

    <T> T post(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        final HttpPost post = new HttpPost(BASE_PATH + path);
        return executeRequest(post, jsonBody, responseHandler);
    }

    private <T> T executeRequest(final HttpEntityEnclosingRequest request,
                                 final Supplier<String> jsonBody,
                                 final HTTPResponseHandler<T> responseMapper) throws Exception {
        final StringEntity stringEntity = getStringEntity(jsonBody);
        request.setEntity(stringEntity);
        return executeRequest((HttpUriRequest) request, responseMapper);
    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private <T> T executeRequest(final HttpUriRequest request, final HTTPResponseHandler<T> responseMapper) throws Exception {
        final CloseableHttpResponse response = httpClient.execute(request);

        final int statusCode = response.getStatusLine().getStatusCode();
        final HttpEntity entity = response.getEntity();
        final String responseBody = convertStreamToString(entity.getContent());
        final T result = responseMapper.handle(statusCode, responseBody);

        response.close();

        return result;
    }

    private StringEntity getStringEntity(final Supplier<String> jsonBody) throws UnsupportedEncodingException {
        final StringEntity input = new StringEntity(jsonBody.get());
        input.setContentType("application/json");
        return input;
    }

}
