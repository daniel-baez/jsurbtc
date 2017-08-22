package cl.daplay.jsurbtc.dto.request;

import cl.daplay.jsurbtc.model.order.OrderPriceType;
import cl.daplay.jsurbtc.model.order.OrderType;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.LinkedHashMap;
import java.util.Map;

public class OrderRequestDTO {

    @JsonProperty("order")
    private final Map<String, Object> content;

    public OrderRequestDTO(final DecimalFormat bigDecimalFormat, final OrderType orderType, final OrderPriceType orderPriceType, final BigDecimal qty, final BigDecimal price) {
        this.content = new LinkedHashMap<>();
        this.content.put("type", orderType);
        this.content.put("price_type", orderPriceType);
        this.content.put("limit", bigDecimalFormat.format(price));
        this.content.put("amount", bigDecimalFormat.format(qty));
    }

}
