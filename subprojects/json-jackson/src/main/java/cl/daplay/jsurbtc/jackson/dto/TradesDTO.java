package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.trades.JacksonTrades;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TradesDTO {

    @JsonProperty("trades")
    private final JacksonTrades trades;

    @JsonCreator
    public TradesDTO(@JsonProperty("trades") JacksonTrades trades) {
        this.trades = trades;
    }

    public JacksonTrades getTrades() {
        return trades;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TradesDTO tradesDTO = (TradesDTO) o;

        return trades != null ? trades.equals(tradesDTO.trades) : tradesDTO.trades == null;
    }

    @Override
    public int hashCode() {
        return trades != null ? trades.hashCode() : 0;
    }
}
