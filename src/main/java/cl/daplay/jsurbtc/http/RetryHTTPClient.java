package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.HTTPClient;
import cl.daplay.jsurbtc.Signer;
import cl.daplay.jsurbtc.fun.ThrowingSupplier;
import cl.daplay.jsurbtc.model.JSurbtcException;

/**
 * RetryHTTPClient it's an HTTPClient delegating inside a retry loop, bound to a hard limit
 */
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
            try {
                return supplier.get();
            } catch (Exception ex) {
                // buda.com sometimes answers 401 by mistake
                if (ex instanceof JSurbtcException) {
                    JSurbtcException surbtcException = (JSurbtcException) ex;
                    boolean invalidCredentials = surbtcException.httpStatusCode == 401;
                    if (!invalidCredentials) {
                        throw ex;
                    }
                }

                lastEx = ex;
                retries = retries + 1;
            }

            if (retries >= limit) {
                throw lastEx;
            }
        }
    }

}
