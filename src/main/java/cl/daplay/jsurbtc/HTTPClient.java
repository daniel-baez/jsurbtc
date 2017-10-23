package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.http.SurbtcHttpRequestInterceptor;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Optional;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

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

        Optional<T> handle(final int statusCode, final String responseBody) throws Exception;

    }

    private final CloseableHttpClient httpClient;

    HTTPClient(final CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    <T> Optional<T> get(final String path, final HTTPResponseHandler<T> responseMapper) {
        final HttpGet get = new HttpGet(BASE_PATH + path);
        return executeRequest(get, responseMapper);
    }

    <T> Optional<T> put(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) {
        final HttpPut put = new HttpPut(BASE_PATH + path);
        return executeRequest(put, jsonBody, responseHandler);
    }

    <T> Optional<T> post(final String path, final Supplier<String> jsonBody, final HTTPResponseHandler<T> responseHandler) {
        final HttpPost post = new HttpPost(BASE_PATH + path);
        return executeRequest(post, jsonBody, responseHandler);
    }

    private <T> Optional<T> executeRequest(final HttpEntityEnclosingRequest request,
                                           final Supplier<String> jsonBody,
                                           final HTTPResponseHandler<T> responseMapper) {
        return getStringEntity(jsonBody)
                .map(entity -> {
                    request.setEntity(entity);
                    return request;
                }).flatMap(req -> executeRequest((HttpUriRequest) req, responseMapper));
    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    private <T> Optional<T> executeRequest(final HttpUriRequest request,
                                           final HTTPResponseHandler<T> responseMapper) {
        try {
            final CloseableHttpResponse response = httpClient.execute(request);

            final int statusCode = response.getStatusLine().getStatusCode();
            final String responseBody = Optional.ofNullable(response)
                    .map(CloseableHttpResponse::getEntity)
                    .map(it -> {
                        try {
                            return it.getContent();
                        } catch (final IOException ex) {
                            LOGGER.log(Level.WARNING, ex, () -> format("While executing HTTP request: '%s'", request));
                            return null;
                        }
                    })
                    .map(this::convertStreamToString)
                    .orElse("");

            final Optional<T> result = responseMapper.handle(statusCode, responseBody);
            response.close();

            return result;
        } catch (final Exception ex) {
            LOGGER.log(Level.WARNING, ex, () -> format("While executing HTTP request: '%s'", request));

            if (ex instanceof JSurbtcException) {
                JSurbtcException EX = (JSurbtcException) ex;

                for (final JSurbtcException.Error error : EX.errors) {
                    LOGGER.log(Level.WARNING, () -> {
                        final String t = "\t Error resource: '%s', field: '%s', code: '%s', message: '%s'";
                        final String m = format(t, error.resource, error.field, error.code, error.message);
                        return m;
                    });
                }
            }


            return Optional.empty();
        }
    }

    private Optional<StringEntity> getStringEntity(final Supplier<String> jsonBody) {
        try {
            final StringEntity input = new StringEntity(jsonBody.get());
            input.setContentType("application/json");

            return Optional.of(input);
        } catch (final Exception ex) {
            LOGGER.log(Level.WARNING, ex, () -> "While creating JSON payload for HTTP request.");
            return Optional.empty();
        }
    }

}
