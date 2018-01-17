package cl.daplay.jsurbtc.jackson.model.trades;

import cl.daplay.jsurbtc.model.Trades;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class JacksonTrades implements Trades, Serializable, Iterable<Trades.Transaction> {

    private static final long serialVersionUID = 2017_10_27;

    @JsonProperty("timestamp")
    private final Instant timestamp;
    @JsonProperty("last_timestamp")
    private final Instant lastTimestamp;
    @JsonProperty("entries")
    private final List<Transaction> entries;

    public JacksonTrades(JacksonTrades other) {
        this.timestamp = other.timestamp;
        this.lastTimestamp = other.lastTimestamp;
        this.entries = other.entries;
    }

    @JsonCreator
    public JacksonTrades(@JsonProperty("timestamp") final Instant timestamp,
                         @JsonProperty("last_timestamp") final Instant lastTimestamp,
                         @JsonProperty("entries") final List<JacksonTransaction> entries) {
        this.timestamp = timestamp;
        this.lastTimestamp = lastTimestamp;
        this.entries = new ArrayList<>(entries);
    }

    public Optional<Instant> getTimestamp() {
        return Optional.ofNullable(timestamp);
    }

    public Optional<Instant> getLastTimestamp() {
        return Optional.ofNullable(lastTimestamp);
    }

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
