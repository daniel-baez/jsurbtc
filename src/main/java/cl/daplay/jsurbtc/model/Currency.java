package cl.daplay.jsurbtc.model;

import java.math.BigDecimal;

public enum Currency {
    USD(new BigDecimal("100")),
    BTC(new BigDecimal(1e8)),
    CLP(new BigDecimal("100")),
    COP(new BigDecimal("100")),
    ETH(new BigDecimal(1e9)),
    PEN(new BigDecimal("100")),
    CNY(new BigDecimal("100"));

    private final BigDecimal subunitsToUnit;

    Currency(final BigDecimal subunitsToUnit) {
        this.subunitsToUnit = subunitsToUnit;
    }

    public BigDecimal getSubunitsToUnit() {
        return subunitsToUnit;
    }
}
