package cl.daplay.jsurbtc.jackson.model.trades;

import cl.daplay.jsurbtc.model.Trades;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JacksonTrades implements Trades, Serializable, Iterable<Trades.Transaction> {

    private static final long serialVersionUID = 2017_10_27;

    @JsonProperty("market_it")
    private final String marketId;
    @JsonProperty("timestamp")
    private final Instant timestamp;
    @JsonProperty("last_timestamp")
    private final Instant lastTimestamp;
    @JsonProperty("entries")
    private final List<Transaction> entries;

    @JsonCreator
    public JacksonTrades(@JsonProperty("market_id") final String marketId,
                         @JsonProperty("timestamp") final Instant timestamp,
                         @JsonProperty("last_timestamp") final Instant lastTimestamp,
                         @JsonProperty("entries") final List<JacksonTransaction> entries) {
        this.marketId = marketId;
        this.timestamp = timestamp;
        this.lastTimestamp = lastTimestamp;
        this.entries = new ArrayList<>(entries);
    }

    @Override
    public String getMarketId() {
        return marketId;
    }

    @Override
    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public Instant getLastTimestamp() {
        return lastTimestamp;
    }

    @Override
    public List<Transaction> getEntries() {
        return entries;
    }

    @Override
    public Iterator<Transaction> iterator() {
        return getEntries().iterator();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final JacksonTrades trades = (JacksonTrades) o;

        if (timestamp != null ? !timestamp.equals(trades.timestamp) : trades.timestamp != null) return false;
        if (lastTimestamp != null ? !lastTimestamp.equals(trades.lastTimestamp) : trades.lastTimestamp != null)
            return false;
        return entries != null ? entries.equals(trades.entries) : trades.entries == null;
    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (lastTimestamp != null ? lastTimestamp.hashCode() : 0);
        result = 31 * result + (entries != null ? entries.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Trades{" +
                "timestamp=" + timestamp +
                ", lastTimestamp=" + lastTimestamp +
                ", entries=" + entries +
                '}';
    }

}