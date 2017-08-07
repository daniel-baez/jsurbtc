package cl.daplay.jsurbtc.model.market;

import cl.daplay.jsurbtc.model.Amount;
import cl.daplay.jsurbtc.model.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class Market implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("id")
    private final MarketID id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("base_currency")
    private final Currency baseCurrency;
    @JsonProperty("quote_currency")
    private final Currency quoteCurrency;
    @JsonProperty("minimum_order_amount")
    private final Amount minimumOrderAmount;

    @JsonCreator
    public Market(@JsonProperty("id") final MarketID id,
                  @JsonProperty("name") final String name,
                  @JsonProperty("base_currency") final Currency baseCurrency,
                  @JsonProperty("quote_currency") final Currency quoteCurrency,
                  @JsonProperty("minimum_order_amount") final Amount minimumOrderAmount) {
        this.id = id;
        this.name = name;
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public MarketID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getQuoteCurrency() {
        return quoteCurrency;
    }

    public Amount getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Market market = (Market) o;

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
