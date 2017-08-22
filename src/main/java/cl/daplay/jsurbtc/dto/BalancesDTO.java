package cl.daplay.jsurbtc.dto;

import cl.daplay.jsurbtc.model.balance.Balance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public final class BalancesDTO {

    @JsonProperty("balances")
    private final List<Balance> balances;

    @JsonCreator
    public BalancesDTO(@JsonProperty("balances") List<Balance> balances) {
        this.balances = balances;
    }

    public List<Balance> getBalances() {
        return balances;
    }
}
