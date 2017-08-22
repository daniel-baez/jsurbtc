package cl.daplay.jsurbtc.model.quotation;

import cl.daplay.jsurbtc.model.market.MarketID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum QuotationType {
    ASK_GIVEN_SPENT_BASE,
    ASK_GIVEN_EARNED_QUOTE,
    BID_GIVEN_EARNED_BASE,
    BID_GIVEN_SPENT_QUOTE;

    @JsonCreator
    public static MarketID fromJsonString(final String value) {
        return MarketID.valueOf(value.replace('-', '_'));
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

}
