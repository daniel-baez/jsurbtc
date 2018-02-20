package cl.daplay.jsurbtc.jackson.model.trades;

import cl.daplay.jsurbtc.model.Trades;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "timestamp", "amount", "price", "direction" })
@JsonIgnoreProperties(ignoreUnknown = true)
public class JacksonTransaction implements Trades.Transaction, Serializable, Comparable<Trades.Transaction> {

    private static final long serialVersionUID = 2017_10_27;

    @JsonProperty("timestamp")
    private final Instant timestamp;
    @JsonProperty("amount")
    private final BigDecimal amount;
    @JsonProperty("price")
    private final BigDecimal price;
    @JsonProperty("direction")
    private final String direction;

    public JacksonTransaction(@JsonProperty("timestamp") final Instant timestamp,
                              @JsonProperty("amount") final BigDecimal amount,
                              @JsonProperty("price") final BigDecimal price,
                              @JsonProperty("direction") final String direction) {
        this.timestamp = timestamp;
        this.amount = amount;
        this.price = price;
        this.direction = direction;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getDirection() {
        return direction;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JacksonTransaction that = (JacksonTransaction) o;
        return Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(price, that.price) &&
                Objects.equals(direction, that.direction);
    }

    @Override
    public int hashCode() {

        return Objects.hash(timestamp, amount, price, direction);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "timestamp=" + timestamp +
                ", amount=" + amount +
                ", price=" + price +
                ", direction=" + direction +
                '}';
    }

    @Override
    public int compareTo(final Trades.Transaction o) {
        return this.timestamp.compareTo(o.getTimestamp());
    }
}

