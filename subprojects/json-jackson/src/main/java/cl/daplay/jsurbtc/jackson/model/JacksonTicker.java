package cl.daplay.jsurbtc.jackson.model;

import cl.daplay.jsurbtc.jackson.BigDecimalToStringSerializer;
import cl.daplay.jsurbtc.model.Ticker;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.math.BigDecimal;

public class JacksonTicker implements Ticker, Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("last_price")
    private final JacksonAmount lastPrice;
    @JsonProperty("min_ask")
    private final JacksonAmount minAsk;
    @JsonProperty("max_bid")
    private final JacksonAmount maxBid;
    @JsonProperty("volume")
    private final JacksonAmount volume;
    @JsonProperty("price_variation_24h")
    @JsonSerialize(using = BigDecimalToStringSerializer.class)
    private final BigDecimal priceVariation24Hours;
    @JsonProperty("price_variation_7d")
    @JsonSerialize(using = BigDecimalToStringSerializer.class)
    private final BigDecimal priceVariation7Days;

    @JsonCreator
    public JacksonTicker(@JsonProperty("last_price") final JacksonAmount lastPrice,
                         @JsonProperty("min_ask") final JacksonAmount minAsk,
                         @JsonProperty("max_bid") final JacksonAmount maxBid,
                         @JsonProperty("volume") final JacksonAmount volume,
                         @JsonProperty("price_variation_24h") final BigDecimal priceVariation24Hours,
                         @JsonProperty("price_variation_7d") final BigDecimal priceVariation7Days) {
        this.lastPrice = lastPrice;
        this.minAsk = minAsk;
        this.maxBid = maxBid;
        this.volume = volume;
        this.priceVariation24Hours = priceVariation24Hours;
        this.priceVariation7Days = priceVariation7Days;
    }

    @Override
    public BigDecimal getLastPrice() {
        return lastPrice.getAmount();
    }

    @Override
    public String getLastPriceCurrency() {
        return lastPrice.getCurrency();
    }

    @Override
    public BigDecimal getMinAsk() {
        return minAsk.getAmount();
    }

    @Override
    public String getMinAskCurrency() {
        return minAsk.getCurrency();
    }

    @Override
    public BigDecimal getMaxBid() {
        return maxBid.getAmount();
    }

    @Override
    public String getMaxBidCurrency() {
        return maxBid.getCurrency();
    }

    @Override
    public BigDecimal getVolume() {
        return volume.getAmount();
    }

    @Override
    public String getVolumeCurrency() {
        return volume.getCurrency();
    }

    public BigDecimal getPriceVariation24Hours() {
        return priceVariation24Hours;
    }

    public BigDecimal getPriceVariation7Days() {
        return priceVariation7Days;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JacksonTicker ticker = (JacksonTicker) o;

        if (lastPrice != null ? !lastPrice.equals(ticker.lastPrice) : ticker.lastPrice != null) return false;
        if (minAsk != null ? !minAsk.equals(ticker.minAsk) : ticker.minAsk != null) return false;
        if (maxBid != null ? !maxBid.equals(ticker.maxBid) : ticker.maxBid != null) return false;
        if (volume != null ? !volume.equals(ticker.volume) : ticker.volume != null) return false;
        if (priceVariation24Hours != null ? !priceVariation24Hours.equals(ticker.priceVariation24Hours) : ticker.priceVariation24Hours != null)
            return false;
        return priceVariation7Days != null ? priceVariation7Days.equals(ticker.priceVariation7Days) : ticker.priceVariation7Days == null;
    }

    @Override
    public int hashCode() {
        int result = lastPrice != null ? lastPrice.hashCode() : 0;
        result = 31 * result + (minAsk != null ? minAsk.hashCode() : 0);
        result = 31 * result + (maxBid != null ? maxBid.hashCode() : 0);
        result = 31 * result + (volume != null ? volume.hashCode() : 0);
        result = 31 * result + (priceVariation24Hours != null ? priceVariation24Hours.hashCode() : 0);
        result = 31 * result + (priceVariation7Days != null ? priceVariation7Days.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticker{" +
                "lastPrice=" + lastPrice +
                ", minAsk=" + minAsk +
                ", maxBid=" + maxBid +
                ", volume=" + volume +
                ", priceVariation24Hours=" + priceVariation24Hours +
                ", priceVariation7Days=" + priceVariation7Days +
                '}';
    }
}
