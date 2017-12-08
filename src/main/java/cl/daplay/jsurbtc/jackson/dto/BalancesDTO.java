package cl.daplay.jsurbtc.jackson.dto;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalancesDTO that = (BalancesDTO) o;

        return balances.equals(that.balances);
    }

    @Override
    public int hashCode() {
        return balances.hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalancesDTO{");
        sb.append("balances=").append(balances);
        sb.append('}');
        return sb.toString();
    }

}
