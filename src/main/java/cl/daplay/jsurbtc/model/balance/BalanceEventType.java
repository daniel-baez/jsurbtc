package cl.daplay.jsurbtc.model.balance;

import cl.daplay.jsurbtc.model.market.MarketID;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum BalanceEventType {
    TRANSACTION,
    TRANSFER_CONFIRMATION,
    WITHDRAWAL_CONFIRM,
    DEPOSIT_CONFIRM;

    @JsonCreator
    public static BalanceEventType fromJsonString(final String value) {
        return BalanceEventType.valueOf(value.toUpperCase());
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name().toLowerCase();
    }

}
