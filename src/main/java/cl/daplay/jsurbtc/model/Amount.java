package cl.daplay.jsurbtc.model;

import cl.daplay.jsurbtc.jackson.BigDecimalToStringSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Objects;

import static java.lang.String.format;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "amount", "currency" })
public class Amount extends BigDecimal implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("currency")
    private final Currency currency;
    @JsonProperty("amount")
    @JsonSerialize(using = BigDecimalToStringSerializer.class)
    private final BigDecimal amount;

    public Amount(Amount other) {
        this(other.currency, other.amount);
    }

    @JsonCreator
    public Amount(@JsonProperty("currency") final Currency currency,
                  @JsonProperty("amount") final BigDecimal amount) {
        super(amount.toBigInteger(), amount.scale());

        Objects.requireNonNull(currency);
        Objects.requireNonNull(amount);

        this.currency = currency;
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Amount add(Amount o) {
        requireSameCurrency(o);
        return new Amount(currency, super.add(o));
    }

    public Amount add(Amount o, MathContext mc) {
        requireSameCurrency(o);
        return new Amount(currency, super.add(o, mc));
    }

    public Amount subtract(Amount subtrahend) {
        requireSameCurrency(subtrahend);
        return new Amount(currency, super.subtract(subtrahend));
    }

    public Amount subtract(Amount subtrahend, MathContext mc) {
        requireSameCurrency(subtrahend);
        return new Amount(currency, super.subtract(subtrahend, mc));
    }

    public Amount multiply(Amount multiplicand) {
        requireSameCurrency(multiplicand);
        return new Amount(currency, super.multiply(multiplicand));
    }

    public Amount multiply(Amount multiplicand, MathContext mc) {
        requireSameCurrency(multiplicand);
        return new Amount(currency, super.multiply(multiplicand, mc));
    }

    public Amount divide(Amount divisor, int scale, int roundingMode) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.divide(divisor, scale, roundingMode));
    }

    public Amount divide(Amount divisor, int scale, RoundingMode roundingMode) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.divide(divisor, scale, roundingMode));
    }

    public Amount divide(Amount divisor, int roundingMode) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.divide(divisor, roundingMode));
    }

    public Amount divide(Amount divisor, RoundingMode roundingMode) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.divide(divisor, roundingMode));
    }

    public Amount divide(Amount divisor) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.divide(divisor));
    }

    public Amount divide(Amount divisor, MathContext mc) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.divide(divisor, mc));
    }

    public Amount remainder(Amount divisor) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.remainder(divisor));
    }

    public Amount remainder(Amount divisor, MathContext mc) {
        requireSameCurrency(divisor);
        return new Amount(currency, super.remainder(divisor, mc));
    }

    @Override
    public Amount add(BigDecimal augend) {
        return new Amount(currency, super.add(augend));
    }

    @Override
    public Amount add(BigDecimal augend, MathContext mc) {
        return new Amount(currency, super.add(augend, mc));
    }

    @Override
    public Amount subtract(BigDecimal subtrahend) {
        return new Amount(currency, super.subtract(subtrahend));
    }

    @Override
    public Amount subtract(BigDecimal subtrahend, MathContext mc) {
        return new Amount(currency, super.subtract(subtrahend, mc));
    }

    @Override
    public Amount multiply(BigDecimal multiplicand) {
        return new Amount(currency, super.multiply(multiplicand));
    }

    @Override
    public Amount multiply(BigDecimal multiplicand, MathContext mc) {
        return new Amount(currency, super.multiply(multiplicand, mc));
    }

    @Override
    public Amount divide(BigDecimal divisor, int scale, int roundingMode) {
        return new Amount(currency, super.divide(divisor, scale, roundingMode));
    }

    @Override
    public Amount divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
        return new Amount(currency, super.divide(divisor, scale, roundingMode));
    }

    @Override
    public Amount divide(BigDecimal divisor, int roundingMode) {
        return new Amount(currency, super.divide(divisor, roundingMode));
    }

    @Override
    public Amount divide(BigDecimal divisor, RoundingMode roundingMode) {
        return new Amount(currency, super.divide(divisor, roundingMode));
    }

    @Override
    public Amount divide(BigDecimal divisor) {
        return new Amount(currency, super.divide(divisor));
    }

    @Override
    public Amount divide(BigDecimal divisor, MathContext mc) {
        return new Amount(currency, super.divide(divisor, mc));
    }

    @Override
    public Amount remainder(BigDecimal divisor) {
        return new Amount(currency, super.remainder(divisor));
    }

    @Override
    public Amount remainder(BigDecimal divisor, MathContext mc) {
        return new Amount(currency, super.remainder(divisor, mc));
    }

    @Override
    public Amount pow(int n) {
        return new Amount(currency, super.pow(n));
    }

    @Override
    public Amount pow(int n, MathContext mc) {
        return new Amount(currency, super.pow(n, mc));
    }

    private void requireSameCurrency(Amount other) {
        if (currency != other.currency) {
            throw new IllegalArgumentException(format("Can't mix currencies: %s, %s", currency, other.currency));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Amount amount = (Amount) o;

        return currency == amount.currency;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "currency=" + currency +
                ", amount=" + format("%.2f", amount) +
                '}';
    }

}

