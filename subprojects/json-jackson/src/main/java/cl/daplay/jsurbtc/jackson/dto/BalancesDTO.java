package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.balance.JacksonBalance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public final class BalancesDTO {

    @JsonProperty("balances")
    private final List<JacksonBalance> balances;

    @JsonCreator
    public BalancesDTO(@JsonProperty("balances") List<JacksonBalance> balances) {
        this.balances = balances;
    }

    public List<JacksonBalance> getBalances() {
        return balances;
    }

}
