package cl.daplay.jsurbtc.jackson.model.market;

import cl.daplay.jsurbtc.jackson.model.JacksonAmount;
import cl.daplay.jsurbtc.model.Market;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JacksonMarket implements Market, Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("id")
    private final String id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("base_currency")
    private final String baseCurrency;
    @JsonProperty("quote_currency")
    private final String quoteCurrency;
    @JsonProperty("minimum_order_amount")
    private final JacksonAmount minimumOrderAmount;

    @JsonCreator
    public JacksonMarket(@JsonProperty("id") final String id,
                         @JsonProperty("name") final String name,
                         @JsonProperty("base_currency") final String baseCurrency,
                         @JsonProperty("quote_currency") final String quoteCurrency,
                         @JsonProperty("minimum_order_amount") final JacksonAmount minimumOrderAmount) {
        this.id = id;
        this.name = name;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.minimumOrderAmount = minimumOrderAmount;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getBaseCurrency() {
        return baseCurrency;
    }

    @Override
    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    @Override
    public BigDecimal getMinimumOrderAmount() {
        return minimumOrderAmount.getAmount();
    }

    @Override
    public String getMinimumOrderAmountCurrency() {
        return minimumOrderAmount.getCurrency();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JacksonMarket market = (JacksonMarket) o;

        if (id != null ? !id.equals(market.id) : market.id != null) return false;
        if (name != null ? !name.equals(market.name) : market.name != null) return false;
        if (baseCurrency != null ? !baseCurrency.equals(market.baseCurrency) : market.baseCurrency != null)
            return false;
        if (quoteCurrency != null ? !quoteCurrency.equals(market.quoteCurrency) : market.quoteCurrency != null)
            return false;
        return minimumOrderAmount != null ? minimumOrderAmount.equals(market.minimumOrderAmount) : market.minimumOrderAmount == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (baseCurrency != null ? baseCurrency.hashCode() : 0);
        result = 31 * result + (quoteCurrency != null ? quoteCurrency.hashCode() : 0);
        result = 31 * result + (minimumOrderAmount != null ? minimumOrderAmount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Market{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", quoteCurrency='" + quoteCurrency + '\'' +
                ", minimumOrderAmount=" + minimumOrderAmount +
                '}';
    }
}
