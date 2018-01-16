package cl.daplay.jsurbtc.jackson.model;

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
public class JacksonAmount extends BigDecimal implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    private static BigDecimal unwrap(BigDecimal o) {
        if (o instanceof JacksonAmount) {
            return ((JacksonAmount) o).getAmount();
        }

        return o;
    }

    @JsonProperty("currency")
    private final String currency;
    @JsonProperty("amount")
    @JsonSerialize(using = BigDecimalToStringSerializer.class)
    private final BigDecimal amount;

    public JacksonAmount(JacksonAmount other) {
        this(other.getCurrency(), other.getAmount());
    }

    @JsonCreator
    public JacksonAmount(@JsonProperty("currency") final String currency,
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
    public JacksonAmount abs() {
        return new JacksonAmount(currency, super.abs());
    }

    @Override
    public JacksonAmount abs(MathContext mc) {
        return new JacksonAmount(currency, super.abs(mc));
    }

    @Override
    public JacksonAmount negate() {
        return new JacksonAmount(currency, super.negate());
    }

    @Override
    public JacksonAmount negate(MathContext mc) {
        return new JacksonAmount(currency, super.negate(mc));
    }

    public JacksonAmount add(JacksonAmount o) {
        return new JacksonAmount(currency, super.add(o));
    }

    public JacksonAmount add(JacksonAmount o, MathContext mc) {
        return new JacksonAmount(currency, super.add(o, mc));
    }

    public JacksonAmount subtract(JacksonAmount subtrahend) {
        return new JacksonAmount(currency, super.subtract(subtrahend));
    }

    public JacksonAmount subtract(JacksonAmount subtrahend, MathContext mc) {
        return new JacksonAmount(currency, super.subtract(subtrahend, mc));
    }

    public JacksonAmount multiply(JacksonAmount multiplicand) {
        return new JacksonAmount(currency, super.multiply(multiplicand));
    }

    public JacksonAmount multiply(JacksonAmount multiplicand, MathContext mc) {
        return new JacksonAmount(currency, super.multiply(multiplicand, mc));
    }

    public JacksonAmount divide(JacksonAmount divisor, int scale, int roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor, scale, roundingMode));
    }

    public JacksonAmount divide(JacksonAmount divisor, int scale, RoundingMode roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor, scale, roundingMode));
    }

    public JacksonAmount divide(JacksonAmount divisor, int roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor, roundingMode));
    }

    public JacksonAmount divide(JacksonAmount divisor, RoundingMode roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor.getAmount(), roundingMode));
    }

    public JacksonAmount divide(JacksonAmount divisor) {
        System.out.println("BBBBBBBB");
        return new JacksonAmount(currency, super.divide(divisor.getAmount(), RoundingMode.HALF_UP));
    }

    public JacksonAmount divide(JacksonAmount divisor, MathContext mc) {
        return new JacksonAmount(currency, super.divide(divisor, mc));
    }

    public JacksonAmount remainder(JacksonAmount divisor) {
        return new JacksonAmount(currency, super.remainder(divisor));
    }

    public JacksonAmount remainder(JacksonAmount divisor, MathContext mc) {
        return new JacksonAmount(currency, super.remainder(divisor, mc));
    }

    @Override
    public JacksonAmount add(BigDecimal augend) {
        return new JacksonAmount(currency, super.add(augend));
    }

    @Override
    public JacksonAmount add(BigDecimal augend, MathContext mc) {
        return new JacksonAmount(currency, super.add(augend, mc));
    }

    @Override
    public JacksonAmount subtract(BigDecimal subtrahend) {
        return new JacksonAmount(currency, super.subtract(subtrahend));
    }

    @Override
    public JacksonAmount subtract(BigDecimal subtrahend, MathContext mc) {
        return new JacksonAmount(currency, super.subtract(subtrahend, mc));
    }

    @Override
    public JacksonAmount multiply(BigDecimal multiplicand) {
        return new JacksonAmount(currency, super.multiply(multiplicand));
    }

    @Override
    public JacksonAmount multiply(BigDecimal multiplicand, MathContext mc) {
        return new JacksonAmount(currency, super.multiply(multiplicand, mc));
    }

    @Override
    public JacksonAmount divide(BigDecimal divisor, int scale, int roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor, scale, roundingMode));
    }

    @Override
    public JacksonAmount divide(BigDecimal divisor, int scale, RoundingMode roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor, scale, roundingMode));
    }

    @Override
    public JacksonAmount divide(BigDecimal divisor, int roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor, roundingMode));
    }

    @Override
    public JacksonAmount divide(BigDecimal divisor, RoundingMode roundingMode) {
        return new JacksonAmount(currency, super.divide(divisor, roundingMode));
    }

    @Override
    public JacksonAmount divide(BigDecimal divisor) {
        System.out.println("AAAAAAA");
        return new JacksonAmount(currency, super.divide(divisor));
    }

    @Override
    public JacksonAmount divide(BigDecimal divisor, MathContext mc) {
        return new JacksonAmount(currency, super.divide(divisor, mc));
    }

    @Override
    public JacksonAmount remainder(BigDecimal divisor) {
        return new JacksonAmount(currency, super.remainder(divisor));
    }

    @Override
    public JacksonAmount remainder(BigDecimal divisor, MathContext mc) {
        return new JacksonAmount(currency, super.remainder(divisor, mc));
    }

    @Override
    public JacksonAmount pow(int n) {
        return new JacksonAmount(currency, super.pow(n));
    }

    @Override
    public JacksonAmount pow(int n, MathContext mc) {
        return new JacksonAmount(currency, super.pow(n, mc));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JacksonAmount amount = (JacksonAmount) o;

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

