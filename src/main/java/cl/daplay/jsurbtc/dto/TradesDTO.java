package cl.daplay.jsurbtc.dto;

import cl.daplay.jsurbtc.model.trades.Trades;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class TradesDTO {

    @JsonProperty("trades")
    private final Trades trades;

    @JsonCreator
    public TradesDTO(@JsonProperty("trades") Trades trades) {
        this.trades = trades;
    }

    public Trades getTrades() {
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
