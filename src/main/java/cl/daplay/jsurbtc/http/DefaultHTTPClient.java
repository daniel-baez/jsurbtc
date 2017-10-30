package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.HTTPClient;
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

import static cl.daplay.jsurbtc.Utils.convertStreamToString;

public final class DefaultHTTPClient implements HTTPClient {

    private static final String BASE_PATH = "https://www.surbtc.com/";
    private final static Logger LOGGER = Logger.getLogger(DefaultHTTPClient.class.getName());

    public static HTTPClient newInstance(final String key,
                                         final String secret,
                                         final LongSupplier nonceSupplier,
                                         final HttpHost proxy) {
        final CloseableHttpClient build = HttpClients.custom()
                // .addInterceptorFirst(new SurbtcHttpRequestInterceptor(key, secret, nonceSupplier))
                .addInterceptorFirst(new SignedHttpRequestInterceptor(key, secret, nonceSupplier))
                .setProxy(proxy)
                .build();

        return new DefaultHTTPClient(build);
    }

    private final CloseableHttpClient httpClient;

    DefaultHTTPClient(final CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public <T> T get(final String path, final HTTPResponseHandler<T> responseMapper) throws Exception {
        final HttpGet get = new HttpGet(BASE_PATH + path);
        return executeRequest(get, responseMapper);
    }

    @Override
    public <T> T put(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        final HttpPut put = new HttpPut(BASE_PATH + path);
        return executeRequest(put, jsonBody, responseHandler);
    }

    @Override
    public <T> T post(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
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
