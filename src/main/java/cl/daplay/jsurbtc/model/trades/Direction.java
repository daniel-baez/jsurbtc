package cl.daplay.jsurbtc.model.trades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Direction {
    BUY,
    SELL;

    @JsonCreator
    public static Direction fromJsonString(final String value) {
        return Direction.valueOf(value.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

}
