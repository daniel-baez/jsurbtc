package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public interface Withdrawal extends Serializable {

    long getId();

    String getState();

    String getCurrency();

    Instant getCreatedAt();

    WithdrawalData getWithdrawalData();

    BigDecimal getAmount();

    String getAmountCurrency();

    BigDecimal getFee();

    String getFeeCurrency();

    interface WithdrawalData extends Serializable {


    }

    interface BitcoinWithdrawalData extends WithdrawalData {

        String getTargetAddress();

        String getTxHash();

    }

    interface FiatWithdrawalData extends WithdrawalData {


        long getId();

        Instant getCreatedAt();

        Instant getUpdatedAt();

        Instant getTransactedAt();

        String getStatementRef();

        Account getAccount();

        Account getSourceAccount();

    }
}

