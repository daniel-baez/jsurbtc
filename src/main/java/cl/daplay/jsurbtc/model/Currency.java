package cl.daplay.jsurbtc.model;

import java.math.BigDecimal;

public enum Currency {
    USD(new BigDecimal("100")),
    BTC(new BigDecimal(1e8), true),
    BCH(new BigDecimal(1e8), true),
    CLP(new BigDecimal("100")),
    COP(new BigDecimal("100")),
    ETH(new BigDecimal(1e9), true),
    PEN(new BigDecimal("100")),
    CNY(new BigDecimal("100"));

    private final BigDecimal subunitsToUnit;
    private final boolean isCrypto;

    Currency(final BigDecimal subunitsToUnit) {
        this(subunitsToUnit, false);
    }

    Currency(final BigDecimal subunitsToUnit, final boolean isCrypto) {
        this.subunitsToUnit = subunitsToUnit;
        this.isCrypto = isCrypto;
    }

    public BigDecimal getSubunitsToUnit() {
        return subunitsToUnit;
    }

    /**
     * @return true if it's a Crypto currency like BTC, BCH, CLP
     */
    public boolean isCrypto() {
        return isCrypto;
    }
}
