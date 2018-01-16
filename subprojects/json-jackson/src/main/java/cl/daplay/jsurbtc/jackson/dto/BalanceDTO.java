package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.balance.JacksonBalance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class BalanceDTO {

    @JsonProperty("balance")
    private final JacksonBalance balance;

    @JsonCreator
    public BalanceDTO(@JsonProperty("balance") JacksonBalance balance) {
        this.balance = balance;
    }

    public JacksonBalance getBalance() {
        return balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalanceDTO that = (BalanceDTO) o;

        return balance.equals(that.balance);
    }

    @Override
    public int hashCode() {
        return balance.hashCode();
    }
}
