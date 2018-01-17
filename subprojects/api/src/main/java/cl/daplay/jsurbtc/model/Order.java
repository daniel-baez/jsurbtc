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

    String getLimitCurrency();

    BigDecimal getAmount();

    String getAmountCurrency();

    BigDecimal getOriginalAmount();

    String getOriginalAmountCurrency();

    BigDecimal getTradedAmount();

    String getTradedAmountCurrency();

    BigDecimal getTotalExchanged();

    String getTotalExchangedCurrency();

    BigDecimal getPaidFee();

    String getPaidFeeCurrency();
}
