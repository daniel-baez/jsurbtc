package cl.daplay.jsurbtc.dto.request;

import cl.daplay.jsurbtc.jackson.BigDecimalToStringSerializer;
import cl.daplay.jsurbtc.model.order.OrderPriceType;
import cl.daplay.jsurbtc.model.order.OrderType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderRequestDTO {

    @JsonProperty("order")
    private final Map<String, Object> content;

    public OrderRequestDTO(final OrderType orderType, final OrderPriceType orderPriceType, final BigDecimal qty, final BigDecimal price) {
        this.content = new LinkedHashMap<>();
        this.content.put("type", orderType);
        this.content.put("price_type", orderPriceType);
        this.content.put("limit", BigDecimalToStringSerializer.newFormat().format(price));
        this.content.put("amount", BigDecimalToStringSerializer.newFormat().format(qty));
    }

}
