package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public interface Order extends Serializable {

    long getId();

    String getType();

    String getState();

    Instant getCreatedAt();

    long getAccountId();

    String getFeeCurrency();

    String getPriceType();

    BigDecimal getLimit();

    BigDecimal getAmount();

    BigDecimal getOriginalAmount();

    BigDecimal getTradedAmount();

    BigDecimal getTotalExchanged();

    BigDecimal getPaidFee();

}
