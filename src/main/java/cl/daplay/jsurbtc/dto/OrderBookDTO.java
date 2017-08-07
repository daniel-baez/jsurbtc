package cl.daplay.jsurbtc.dto;

import cl.daplay.jsurbtc.model.order.OrderBook;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class OrderBookDTO {

    @JsonProperty("order_book")
    private final OrderBook orderBook;

    @JsonCreator
    public OrderBookDTO(@JsonProperty("order_book") OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

}
