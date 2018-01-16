package cl.daplay.jsurbtc.model.withdrawal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public interface Withdrawal extends Serializable {

    long getId();

    String getState();

    String getCurrency();

    Instant getCreatedAt();

    WithdrawalData getWithdrawalWithdrawalData();

    BigDecimal getAmount();

    BigDecimal getFee();

}

