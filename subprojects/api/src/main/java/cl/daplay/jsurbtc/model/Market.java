package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Market extends Serializable {

    String getId();

    String getName();

    String getBaseCurrency();

    String getQuoteCurrency();

    BigDecimal getMinimumOrderAmount();

    String getMinimumOrderAmountCurrency();
}
