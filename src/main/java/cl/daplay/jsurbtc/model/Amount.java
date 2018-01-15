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

    private static BigDecimal unwrap(BigDecimal o) {
        if (o instanceof Amount) {
            return ((Amount) o).getAmount();
        }

        return o;
    }

    @JsonProperty("currency")
    private final String currency;
    @JsonProperty("amount")
    @JsonSerialize(using = BigDecimalToStringSerializer.class)
    private final BigDecimal amount;

    public Amount(Amount other) {
        this(other.getCurrency(), other.getAmount());
    }

    @JsonCreator
    public Amount(@JsonProperty("currency") final String currency,
                  @JsonProperty("amount") final BigDecimal amount) {
        super(unwrap(amount).toString());

        Objects.requireNonNull(currency);
        Objects.requireNonNull(amount);

        this.currency = currency;
        this.amount = unwrap(amount);
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public Amount abs() {
        return new Amount(currency, super.abs());
    }

    @Override
    public Amount abs(MathContext mc) {
        return new Amount(currency, super.abs(mc));
    }

    @Override
    public Amount negate() {
        return new Amount(currency, super.negate());
    }

    @Override
    public Amount negate(MathContext mc) {
        return new Amount(currency, super.negate(mc));
    }

    public Amount add(Amount o) {
        return new Amount(currency, super.add(o));
    }

    public Amount add(Amount o, MathContext mc) {
        return new Amount(currency, super.add(o, mc));
    }

    public Amount subtract(Amount subtrahend) {
        return new Amount(currency, super.subtract(subtrahend));
    }

    public Amount subtract(Amount subtrahend, MathContext mc) {
        return new Amount(currency, super.subtract(subtrahend, mc));
    }

    public Amount multiply(Amount multiplicand) {
        return new Amount(currency, super.multiply(multiplicand));
    }

    public Amount multiply(Amount multiplicand, MathContext mc) {
        return new Amount(currency, super.multiply(multiplicand, mc));
    }

    public Amount divide(Amount divisor, int scale, int roundingMode) {
        return new Amount(currency, super.divide(divisor, scale, roundingMode));
    }

    public Amount divide(Amount divisor, int scale, RoundingMode roundingMode) {
        return new Amount(currency, super.divide(divisor, scale, roundingMode));
    }

    public Amount divide(Amount divisor, int roundingMode) {
        return new Amount(currency, super.divide(divisor, roundingMode));
    }

    public Amount divide(Amount divisor, RoundingMode roundingMode) {
        return new Amount(currency, super.divide(divisor.getAmount(), roundingMode));
    }

    public Amount divide(Amount divisor) {
        return new Amount(currency, super.divide(divisor.getAmount(), RoundingMode.HALF_UP));
    }

    public Amount divide(Amount divisor, MathContext mc) {
        return new Amount(currency, super.divide(divisor, mc));
    }

    public Amount remainder(Amount divisor) {
        return new Amount(currency, super.remainder(divisor));
    }

    public Amount remainder(Amount divisor, MathContext mc) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Amount amount = (Amount) o;

        return currency.equals(amount.currency);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    @Override
    public final String toString() {
        return format("%s %s", currency, super.toString());
    }

}

