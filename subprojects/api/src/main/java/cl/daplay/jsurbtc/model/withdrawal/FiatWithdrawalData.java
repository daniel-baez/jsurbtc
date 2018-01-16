package cl.daplay.jsurbtc.model.withdrawal;

import java.time.Instant;

public interface FiatWithdrawalData extends WithdrawalData {


    long getId();

    Instant getCreatedAt();

    Instant getUpdatedAt();

    Instant getTransactedAt();

    String getStatementRef();

    Account getAccount();

    Account getSourceAccount();

}
