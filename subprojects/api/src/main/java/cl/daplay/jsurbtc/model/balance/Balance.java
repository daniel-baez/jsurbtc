package cl.daplay.jsurbtc.model.balance;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Balance extends Serializable {

    long getAccountId();

    String getId();

    BigDecimal getAmount();

    BigDecimal getAvailableAmount();

    BigDecimal getFrozenAmount();

    BigDecimal getPendingWithdrawAmount();

}

