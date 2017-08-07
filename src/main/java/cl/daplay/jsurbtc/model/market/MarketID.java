package cl.daplay.jsurbtc.model.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum MarketID {
    BTC_CLP,
    BTC_COP,
    ETH_CLP,
    ETH_BTC;

    @JsonCreator
    public static MarketID fromJsonString(final String value) {
        return MarketID.valueOf(value.replace('-', '_'));
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name().replace('_', '-');
    }

}
