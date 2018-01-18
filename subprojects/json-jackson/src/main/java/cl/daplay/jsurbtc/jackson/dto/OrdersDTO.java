package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.order.JacksonOrder;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonPropertyOrder({ "orders", "meta" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class OrdersDTO {

    @JsonProperty("orders")
    private final List<JacksonOrder> orders;

    @JsonCreator
    public OrdersDTO(@JsonProperty("orders") List<JacksonOrder> orders) {
        this.orders = orders;
    }

    public List<JacksonOrder> getOrders() {
        return orders;
    }
}
