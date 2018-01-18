package cl.daplay.jsurbtc.jackson.model.order;

import cl.daplay.jsurbtc.model.OrderBook;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({ "asks", "bids" })
public class JacksonOrderBook implements OrderBook, Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("bids")
    private final List<Offer> bids;
    @JsonProperty("asks")
    private final List<Offer> asks;

    @JsonCreator
    public JacksonOrderBook(@JsonProperty("bids") List<JacksonOffer> bids,
                            @JsonProperty("asks") List<JacksonOffer> asks) {
        this.bids = new ArrayList<>(bids);
        this.asks = new ArrayList<>(asks);
    }

    public List<Offer> getBids() {
        return bids;
    }

    public List<Offer> getAsks() {
        return asks;
    }

    @Override
    public String toString() {
        return "OrderBook{" +
                "bids=" + bids +
                ", asks=" + asks +
                '}';
    }
}
