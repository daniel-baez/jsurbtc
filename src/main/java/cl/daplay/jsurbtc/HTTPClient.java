package cl.daplay.jsurbtc;

import java.util.function.Supplier;

public interface HTTPClient {

    @FunctionalInterface
    interface HTTPResponseHandler<T> {

        T handle(final int statusCode, final String responseBody) throws Exception;

    }

    <T> T get(String path, HTTPResponseHandler<T> responseMapper) throws Exception;

    <T> T put(String path, Supplier<String> jsonBody, HTTPResponseHandler<T> responseHandler) throws Exception;

    <T> T post(String path, Supplier<String> jsonBody, HTTPResponseHandler<T> responseHandler) throws Exception;


}
