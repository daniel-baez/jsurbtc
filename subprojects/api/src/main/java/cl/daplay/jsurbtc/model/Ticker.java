package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Ticker extends Serializable {

    BigDecimal getLastPrice();

    String getLastPriceCurrency();

    /**
     * @return Lowest selling position
     */
    BigDecimal getMinAsk();

    String getMinAskCurrency();

    /**
     * @return Highest buying position
     */
    BigDecimal getMaxBid();

    String getMaxBidCurrency();

    BigDecimal getVolume();

    String getVolumeCurrency();

    BigDecimal getPriceVariation24Hours();

    BigDecimal getPriceVariation7Days();

}
