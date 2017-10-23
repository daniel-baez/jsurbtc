package cl.daplay.jsurbtc;

import org.junit.Test;

import java.util.function.LongSupplier;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

public class Nonce_UT {

    @Test
    public void nonce_returns_incremental_values() {
        final LongSupplier nonceSupplier = JSurbtc.newNonce();
        final int iterations = 1000000;

        long last = -1;

        for (int i = 0; i < iterations; i++) {
            final long current = nonceSupplier.getAsLong();

            final boolean valuesAreEqual = current == last;
            final boolean currentGreaterThanLast = current > last;

            final boolean validIteration = !valuesAreEqual && currentGreaterThanLast;
            if (validIteration) {
                last = current;
            } else {
                final String t = "Nonce returned same twice. at iteration: %d/%d, current: %d, last: %d";
                final String m = format(t, i, iterations, current, last);
                assertTrue(m, validIteration);
            }
        }

    }

}
