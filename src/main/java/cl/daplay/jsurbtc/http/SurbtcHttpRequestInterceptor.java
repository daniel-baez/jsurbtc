package cl.daplay.jsurbtc.http;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.LongSupplier;
import java.util.Base64;

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

        final String signature = signatureForRequest(request, method, path, nonce);

        request.addHeader("X-SBTC-APIKEY", key);
        request.addHeader("X-SBTC-NONCE", Long.toString(nonce, 10));
        request.addHeader("X-SBTC-SIGNATURE", signature);

        request.addHeader("Content-Type", "application/json");
        request.addHeader("accept", "application/json");
    }

    private String signatureForRequest(HttpRequest request, String method, String path, long nonce) throws IOException {
        final boolean containsBody = request instanceof HttpEntityEnclosingRequest;

        if (!containsBody) {
            final String format = String.format("%s %s %d", method, path, nonce);
            final byte[] bytes = HmacUtils.hmacSha384(secret.getBytes(), format.getBytes());

            return Hex.encodeHexString(bytes);
        }

        final HttpEntityEnclosingRequest entityEnclosingRequest = (HttpEntityEnclosingRequest) request;

        final ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        entityEnclosingRequest.getEntity().writeTo(outstream);
        final String base64EncodedBody = Base64.getEncoder().encodeToString(outstream.toByteArray());

        final String format = String.format("%s %s %s %d", method, path, base64EncodedBody, nonce);
        final byte[] bytes = HmacUtils.hmacSha384(secret.getBytes(), format.getBytes());

        return Hex.encodeHexString(bytes);
    }

}
