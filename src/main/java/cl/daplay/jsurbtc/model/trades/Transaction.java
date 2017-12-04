package cl.daplay.jsurbtc.model.trades;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "timestamp", "amount", "price", "direction" })
public class Transaction implements Serializable, Comparable<Transaction> {

    private static final long serialVersionUID = 2017_10_27;

    @JsonProperty("timestamp")
    private final Instant timestamp;
    @JsonProperty("amount")
    private final BigDecimal amount;
    @JsonProperty("price")
    private final BigDecimal price;
    @JsonProperty("direction")
    private final Direction direction;

    public Transaction(@JsonProperty("timestamp") final Instant timestamp,
                       @JsonProperty("amount") final BigDecimal amount,
                       @JsonProperty("price") final BigDecimal price,
                       @JsonProperty("direction") final Direction direction) {
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

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Transaction that = (Transaction) o;

        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return direction == that.direction;
    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
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
    public int compareTo(final Transaction o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}

