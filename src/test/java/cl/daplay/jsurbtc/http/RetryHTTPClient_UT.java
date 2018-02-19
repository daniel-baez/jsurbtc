package cl.daplay.jsurbtc.http;

import cl.daplay.jsurbtc.HTTPClient;
import cl.daplay.jsurbtc.Signer;
import cl.daplay.jsurbtc.fun.ThrowingSupplier;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class RetryHTTPClient_UT {

    public static final int LIMIT = new Random().nextInt(10) + 1;

    Map<String, AtomicInteger> counters = new HashMap<>();

    AtomicInteger getCounter(String name) {
        return counters.computeIfAbsent(name, __ -> new AtomicInteger());
    }

    HTTPClient httpClient = new HTTPClient() {
        @Override
        public <T> T get(String path, Signer signer, HTTPResponseHandler<T> responseMapper) throws Exception {
            getCounter("get").incrementAndGet();
            throw new Exception();
        }

        @Override
        public <T> T put(String path, Signer signer, String jsonBody, HTTPResponseHandler<T> responseHandler) throws Exception {
            getCounter("put").incrementAndGet();
            throw new Exception();
        }

        @Override
        public <T> T post(String path, Signer signer, String jsonBody, HTTPResponseHandler<T> responseHandler) throws Exception {
            getCounter("post").incrementAndGet();
            throw new Exception();
        }
    };

    RetryHTTPClient retryClient = new RetryHTTPClient(httpClient, LIMIT);

    @Before
    public void before() {
        counters.clear();
    }

    @Test
    public void test_get() {
        testCounter("get", LIMIT, () -> {
            return retryClient.get("path", null, null);
        });
    }

    @Test
    public void test_put() {
        testCounter("put", LIMIT, () -> {
            return retryClient.put("path", null, null, null);
        });
    }

    @Test
    public void test_post() {
        testCounter("post", LIMIT, () -> {
            return retryClient.post("path", null, null, null);
        });
    }

    private void testCounter(String counterName, int expected, ThrowingSupplier supplier) {
        assertEquals(0, getCounter(counterName).get());

        try {
            supplier.get();
        } catch (Exception ex) {
            assertEquals(expected, getCounter(counterName).get());
        }
    }

}
