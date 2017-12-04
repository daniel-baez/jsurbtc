package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.market.Market;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public final class MarketsDTO {

    @JsonProperty("markets")
    private final List<Market> markets;

    @JsonCreator
    public MarketsDTO(@JsonProperty("markets") List<Market> markets) {
        this.markets = markets;
    }

    public List<Market> getMarkets() {
        return markets;
    }

}
