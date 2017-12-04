package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.balance.BalanceEvent;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public final class BalanceEventsDTO {

    @JsonProperty("balance_events")
    private final List<BalanceEvent> balanceEvents;
    @JsonProperty("total_count")
    private final int totalCount;

    @JsonCreator
    public BalanceEventsDTO(@JsonProperty("balance_events") final List<BalanceEvent> balanceEvents,
                            @JsonProperty("total_count") final int totalCount) {
        this.balanceEvents = balanceEvents;
        this.totalCount = totalCount;
    }

    public List<BalanceEvent> getBalanceEvents() {
        return balanceEvents;
    }

    public int getTotalCount() {
        return totalCount;
    }

}
