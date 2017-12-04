package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.balance.Balance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class BalanceDTO {

    @JsonProperty("balance")
    private final Balance balance;

    @JsonCreator
    public BalanceDTO(@JsonProperty("balance") Balance balance) {
        this.balance = balance;
    }

    public Balance getBalance() {
        return balance;
    }

}
