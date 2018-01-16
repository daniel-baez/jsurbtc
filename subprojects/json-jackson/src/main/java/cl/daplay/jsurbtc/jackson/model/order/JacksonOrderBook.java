package cl.daplay.jsurbtc.jackson.model.order;

import cl.daplay.jsurbtc.model.OrderBook;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

@JsonPropertyOrder({ "asks", "bids" })
public class JacksonOrderBook implements OrderBook, Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("bids")
    private final List<Offer> bids;
    @JsonProperty("asks")
    private final List<Offer> asks;

    public JacksonOrderBook(JacksonOrderBook other) {
        this.bids = other.bids;
        this.asks = other.asks;
    }

    @JsonCreator
    public JacksonOrderBook(@JsonProperty("bids") List<Offer> bids,
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
