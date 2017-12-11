package cl.daplay.jsurbtc;

import java.util.function.Supplier;

public interface HTTPClient {

    @FunctionalInterface
    interface HTTPResponseHandler<T> {

        T handle(final int statusCode, final String responseBody) throws Exception;

    }

    <T> T get(String path, Signer signer, HTTPResponseHandler<T> responseMapper) throws Exception;

    <T> T put(String path, Signer signer, Supplier<String> jsonBody, HTTPResponseHandler<T> responseHandler) throws Exception;

    <T> T post(String path, Signer signer, Supplier<String> jsonBody, HTTPResponseHandler<T> responseHandler) throws Exception;


}
