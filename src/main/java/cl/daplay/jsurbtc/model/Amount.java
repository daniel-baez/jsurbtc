package cl.daplay.jsurbtc.model;

import cl.daplay.jsurbtc.jackson.BigDecimalToStringSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

import static java.lang.String.format;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "amount", "currency" })
public class Amount implements Serializable, Comparable<Amount> {

    private static final long serialVersionUID = 2017_08_06;

    private final Currency currency;
    @JsonSerialize(using = BigDecimalToStringSerializer.class)
    private final BigDecimal amount;

    public Amount(Amount other) {
        this.currency = other.currency;
        this.amount = other.amount;
    }

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

    @Override
    public int compareTo(Amount o) {
        sameCurrencyCheck(o);
        return this.getAmount().compareTo(o.getAmount());
    }

    private void sameCurrencyCheck(Amount amount) {
        if (this.getCurrency() == amount.getCurrency()) {
            return;
        }

        final String t = "Can't mix currencies: %s, %s";
        final String m = format(t, this.getCurrency(), amount.getCurrency());
        throw new IllegalArgumentException(m);
    }

}

