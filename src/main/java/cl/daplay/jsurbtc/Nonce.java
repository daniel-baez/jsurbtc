package cl.daplay.jsurbtc;

import java.util.function.LongSupplier;

public enum Nonce implements LongSupplier {
    INSTANCE;

    @Override
    public long getAsLong() {
        return System.currentTimeMillis() / 1000;
    }
    
}
