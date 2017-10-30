package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.Utils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.function.LongSupplier;

public class SurbtcHttpRequestInterceptor implements HttpRequestInterceptor {

    private final String key;
    private final String secret;
    private final LongSupplier nonceSupplier;

    public SurbtcHttpRequestInterceptor(final String key,
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
        final String message = getMessage(body, method, path, nonce);
        final String signature = signMessage(message);

        System.out.printf("key: %s%n", key);
        System.out.printf("secret: %s%n", secret);
        System.out.printf("path: %s%n", path);
        System.out.printf("nonce: %s%n", nonce);

        System.out.printf("message: %s%n", message);
        System.out.printf("signature: %s%n", signature);

        request.addHeader("X-SBTC-APIKEY", key);
        request.addHeader("X-SBTC-NONCE", Long.toString(nonce, 10));
        request.addHeader("X-SBTC-SIGNATURE", signature);

        System.out.printf("X-SBTC-APIKEY: %s%n", key);
        System.out.printf("X-SBTC-NONCE: %s%n", Long.toString(nonce, 10));
        System.out.printf("X-SBTC-SIGNATURE: %s%n", signature);

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

    private String signMessage(final String message) {
        final byte[] bytes = HmacUtils.hmacSha384(secret.getBytes(), message.getBytes());
        return Hex.encodeHexString(bytes);
    }

    private String getMessage(final String body,
                              final String method,
                              final String path,
                              final long nonce) throws IOException {
        final boolean containsBody = !body.isEmpty();

        if (containsBody) {
            final String base64EncodedBody = Base64.getEncoder().encodeToString(body.getBytes());
            return String.format("%s %s %s %d", method, path, base64EncodedBody, nonce);
        } else {
            return String.format("%s %s %d", method, path, nonce);
        }
    }

}
