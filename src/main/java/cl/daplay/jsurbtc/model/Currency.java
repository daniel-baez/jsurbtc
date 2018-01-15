package cl.daplay.jsurbtc.model;

public enum Currency {
    USD,
    BTC(true),
    BCH(true),
    CLP,
    COP,
    ETH(true),
    PEN,
    CNY;

    private final boolean isCrypto;

    Currency() {
        this(false);
    }

    Currency(final boolean isCrypto) {
        this.isCrypto = isCrypto;
    }

    /**
     * @return true if it's a Crypto currency like BTC, BCH, CLP
     */
    public boolean isCrypto() {
        return isCrypto;
    }
}
