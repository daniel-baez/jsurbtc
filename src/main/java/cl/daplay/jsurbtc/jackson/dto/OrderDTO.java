package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.order.Order;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class OrderDTO {

    @JsonProperty("order")
    private final Order order;

    @JsonCreator
    public OrderDTO(@JsonProperty("order") Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        return order.equals(orderDTO.order);
    }

    @Override
    public int hashCode() {
        return order.hashCode();
    }
}
