package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.order.Order;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrdersDTO{");
        sb.append("orders=").append(orders);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrdersDTO ordersDTO = (OrdersDTO) o;

        return orders.equals(ordersDTO.orders);
    }

    @Override
    public int hashCode() {
        return orders.hashCode();
    }
}
