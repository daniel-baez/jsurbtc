package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.order.JacksonOrderBook;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class OrderBookDTO {

    @JsonProperty("order_book")
    private final JacksonOrderBook orderBook;

    @JsonCreator
    public OrderBookDTO(@JsonProperty("order_book") JacksonOrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public JacksonOrderBook getOrderBook() {
        return orderBook;
    }

}
