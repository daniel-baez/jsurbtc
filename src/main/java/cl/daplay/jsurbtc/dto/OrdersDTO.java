package cl.daplay.jsurbtc.dto;

import cl.daplay.jsurbtc.model.order.Order;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@JsonPropertyOrder({ "orders", "meta" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class OrdersDTO {

    @JsonProperty("orders")
    private final List<Order> orders;
    @JsonProperty("meta")
    private final PaginationDTO pagination;

    @JsonCreator
    public OrdersDTO(@JsonProperty("orders") List<Order> orders,
                     @JsonProperty("meta") PaginationDTO pagination) {
        this.orders = orders;
        this.pagination = pagination;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public List<Order> getOrders() {
        return orders;
    }

}
