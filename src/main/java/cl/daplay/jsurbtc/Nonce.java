package cl.daplay.jsurbtc;

import java.util.function.LongSupplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Nonce implements LongSupplier {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(Nonce.class);

    @Override
    public long getAsLong() {
        try {
            Thread.sleep(1);
        } catch (final InterruptedException ex) {
            logger.warn("Thread interruption detected while calling Nonce", ex);
        } finally {
            return System.currentTimeMillis();
        }
    }
    
}
