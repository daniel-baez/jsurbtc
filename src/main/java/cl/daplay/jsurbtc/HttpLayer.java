package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.http.SurbtcHttpRequestInterceptor;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.function.LongSupplier;

final class HttpLayer {

    private static final String BASE_PATH = "https://www.surbtc.com/";

    static HttpLayer newInstance(String key, String secret, LongSupplier nonceSupplier, HttpHost proxy) {
        final CloseableHttpClient build = HttpClients.custom()
                .addInterceptorFirst(new SurbtcHttpRequestInterceptor(key, secret, nonceSupplier))
                .setProxy(proxy)
                .build();

        return new HttpLayer(build);
    }

    private final CloseableHttpClient httpClient;

    HttpLayer(final CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    CloseableHttpResponse get(final String path) throws Exception {
        final HttpGet get = new HttpGet(BASE_PATH + path);
        return httpClient.execute(get);
    }

    CloseableHttpResponse put(final String path, final String jsonBody) throws Exception {
        final HttpPut post = new HttpPut(BASE_PATH + path);
        final StringEntity input = new StringEntity(jsonBody);

        input.setContentType("application/json");
        post.setEntity(input);

        return httpClient.execute(post);
    }

    CloseableHttpResponse post(final String path, final String jsonBody) throws Exception {
        final HttpPost post = new HttpPost(BASE_PATH + path);
        final StringEntity input = new StringEntity(jsonBody);

        input.setContentType("application/json");
        post.setEntity(input);

        return httpClient.execute(post);
    }

}
