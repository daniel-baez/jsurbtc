package cl.daplay.jsurbtc.model.deposit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public interface Deposit extends Serializable {

    long getId();

    String getState();

    BigDecimal getAmount();

    String getCurrency();

    Instant getCreatedAt();

    DepositData getDepositData();

    BigDecimal getFee();

}
