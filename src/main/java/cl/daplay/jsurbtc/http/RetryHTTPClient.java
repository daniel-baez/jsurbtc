package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.HTTPClient;
import cl.daplay.jsurbtc.Signer;
import cl.daplay.jsurbtc.fun.ThrowingSupplier;

import java.util.function.Supplier;
import java.util.logging.Logger;

public final class RetryHTTPClient implements HTTPClient {

    private final HTTPClient delegate;
    private final int limit;

    public RetryHTTPClient(HTTPClient delegate, int limit) {
        this.delegate = delegate;
        this.limit = limit;
    }

    @Override
    public <T> T get(final String path, final Signer signer, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return retry(() -> delegate.get(path, signer, responseHandler));
    }

    @Override
    public <T> T put(final String path, final Signer signer, final String jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return retry(() -> delegate.put(path, signer, jsonBody, responseHandler));
    }

    @Override
    public <T> T post(final String path, final Signer signer, final String jsonBody, final HTTPResponseHandler<T> responseHandler) throws Exception {
        return retry(() -> delegate.post(path, signer, jsonBody, responseHandler));
    }

    private <T> T retry(final ThrowingSupplier<T> supplier) throws Exception {
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

}
