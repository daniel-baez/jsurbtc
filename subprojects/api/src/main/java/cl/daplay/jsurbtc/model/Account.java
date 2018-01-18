package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.time.Instant;

public interface Account extends Serializable {

    long getId();

    String getAccountNumber();

    String getAccountType();

    long getBankId();

    Instant getCreatedAt();

    String getCurrency();

    String getDocumentNumber();

    String getEmail();

    String getFullName();

    String getNationalNumberIdentifier();

    String getPhone();

    Instant getUpdatedAt();

    String getBankName();

    String getPeCciNumber();

}


