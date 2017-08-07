package cl.daplay.jsurbtc;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

public class Nonce_UT {

    @Test
    public void test_nonce_generation() {
        final int SIZE = 10;
        final long[] arr = IntStream.range(0, SIZE).mapToLong(i -> Nonce.INSTANCE.getAsLong()).toArray();

        for (int i = 0; i < SIZE - 1; i++) {
            final int next = i + 1;
            assertTrue("Nonce should always be in incrementing order", arr[next] > arr[i]);
        }
    }

}
