package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.Signer;
import cl.daplay.jsurbtc.Utils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.function.LongSupplier;

public class SignedHttpRequestInterceptor implements HttpRequestInterceptor {

    private final String key;
    private final String secret;
    private final LongSupplier nonceSupplier;

    public SignedHttpRequestInterceptor(final String key,
                                        final String secret,
                                        final LongSupplier nonceSupplier) {
        this.key = key;
        this.secret = secret;
        this.nonceSupplier = nonceSupplier;
    }

    @Override
    public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
        final String method = request.getRequestLine().getMethod();
        final String path = request.getRequestLine().getUri();
        final long nonce = nonceSupplier.getAsLong();

        final String body = getBodyFromRequest(request);
        final String signature;

        try {
            signature = new Signer(secret).sign(body, method, path, nonce);

            request.addHeader("X-SBTC-APIKEY", key);
            request.addHeader("X-SBTC-NONCE", Long.toString(nonce, 10));
            request.addHeader("X-SBTC-SIGNATURE", signature);
        } catch (InvalidKeyException|NoSuchAlgorithmException  e) {
            e.printStackTrace();
        }

        request.addHeader("Content-Type", "application/json");
        request.addHeader("accept", "application/json");
    }

    private String getBodyFromRequest(final HttpRequest request) {
        return Optional.ofNullable(request)
                    .filter(it -> it instanceof HttpEntityEnclosingRequest)
                    .map(it -> (HttpEntityEnclosingRequest) it)
                    .map(it -> {
                        try {
                            return Utils.convertStreamToString(it.getEntity().getContent());
                        } catch (IOException e) {
                            return null;
                        }
                    })
                    .orElse("");
    }

}
