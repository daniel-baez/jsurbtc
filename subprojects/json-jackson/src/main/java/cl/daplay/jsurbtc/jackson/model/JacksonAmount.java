package cl.daplay.jsurbtc.jackson.model;

import cl.daplay.jsurbtc.jackson.BigDecimalToStringSerializer;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonPropertyOrder({ "amount", "currency" })
public class JacksonAmount {

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
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}

