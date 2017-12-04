package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.Ticker;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class TickerDTO {

    @JsonProperty("ticker")
    private final Ticker ticker;

    @JsonCreator
    public TickerDTO(@JsonProperty("ticker") Ticker ticker) {
        this.ticker = ticker;
    }

    public Ticker getTicker() {
        return ticker;
    }
}
