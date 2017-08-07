package cl.daplay.jsurbtc.model;

import cl.daplay.jsurbtc.jackson.BigDecimalToStringSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "amount", "currency" })
public final class Amount implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    private final Currency currency;
    @JsonSerialize(using = BigDecimalToStringSerializer.class)
    private final BigDecimal amount;

    @JsonCreator
    public Amount(@JsonProperty("currency") final Currency currency,
                  @JsonProperty("amount") final BigDecimal amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Amount amount1 = (Amount) o;

        if (currency != null ? !currency.equals(amount1.currency) : amount1.currency != null) return false;
        return amount != null ? amount.equals(amount1.amount) : amount1.amount == null;
    }

    @Override
    public int hashCode() {
        int result = currency != null ? currency.hashCode() : 0;
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "currency=" + currency +
                ", amount=" + amount +
                '}';
    }

}

