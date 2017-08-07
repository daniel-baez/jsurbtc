package cl.daplay.jsurbtc.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderPriceType {
    MARKET,
    LIMIT;

    @JsonCreator
    public static OrderType fromJsonString(final String value) {
        return OrderType.valueOf(value.toUpperCase());
    }

    @JsonValue
    @Override
    public String toString() {
        return this.name().toLowerCase();
    }
}
