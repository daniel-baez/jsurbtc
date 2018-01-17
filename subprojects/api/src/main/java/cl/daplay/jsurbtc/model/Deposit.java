package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

public interface Deposit extends Serializable {

    long getId();

    String getState();

    BigDecimal getAmount();

    String getAmountCurrency();

    String getCurrency();

    Instant getCreatedAt();

    DepositData getDepositData();

    BigDecimal getFee();

    String getFeeCurrency();

    interface DepositData extends Serializable {

        String getType();

        Optional<String> getAddress();

        Optional<String> getTxHash();

        Optional<Instant> getCreatedAt();

        Optional<Instant> getUpdatedAt();

        Optional<String> getUploadUrl();
    }
}
