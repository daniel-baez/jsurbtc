package cl.daplay.jsurbtc.model.order;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum OrderState {
    RECEIVED,
    PENDING,
    TRADED,
    CANCELING,
    CANCELED;

    @JsonCreator
    public static OrderState fromJsonString(final String value) {
        return OrderState.valueOf(value.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }
}
