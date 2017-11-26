package cl.daplay.jsurbtc.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({ "asks", "bids" })
public final class OrderBook implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("bids")
    private final List<Offer> bids;
    @JsonProperty("asks")
    private final List<Offer> asks;

    @JsonCreator
    public OrderBook(@JsonProperty("bids") List<Offer> bids,
                     @JsonProperty("asks") List<Offer> asks) {
        this.bids = bids;
        this.asks = asks;
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
