package cl.daplay.jsurbtc.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.UnsupportedEncodingException;
import java.util.function.LongSupplier;

public class JSurbtcHttpClient {

    private static final String BASE_PATH = "https://www.surbtc.com/";

    private static CloseableHttpClient buildHttpClient(String key, String secret, LongSupplier nonceSupplier) {
        return HttpClients.custom()
                .setProxy(new HttpHost("localhost", 8888))
                .addInterceptorFirst(new SurbtcHttpRequestInterceptor(key, secret, nonceSupplier))
                .build();
    }

    private final CloseableHttpClient httpClient;
    private final ObjectMapper objectMapper;

    public JSurbtcHttpClient(ObjectMapper objectMapper, String key, String secret, LongSupplier nonceSupplier) {
        this.httpClient = buildHttpClient(key, secret, nonceSupplier);
        this.objectMapper = objectMapper;
    }

    private CloseableHttpResponse get(final String path) throws Exception {
        final HttpGet get = new HttpGet(BASE_PATH + path);
        return httpClient.execute(get);
    }

    private CloseableHttpResponse put(final String path, final Object payload) throws Exception {
        final HttpPut post = new HttpPut(BASE_PATH + path);
        writePayload(post, payload);
        return httpClient.execute(post);
    }

    private CloseableHttpResponse post(final String path, final Object payload) throws Exception {
        final HttpPost post = new HttpPost(BASE_PATH + path);
        writePayload(post, payload);
        return httpClient.execute(post);
    }

    private void writePayload(HttpEntityEnclosingRequest request, Object payload) throws UnsupportedEncodingException, JsonProcessingException {
        final StringEntity input = new StringEntity(objectMapper.writeValueAsString(payload));
        input.setContentType("application/json");
        request.setEntity(input);
    }

}
