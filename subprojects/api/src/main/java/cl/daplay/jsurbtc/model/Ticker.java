package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Ticker extends Serializable {

    BigDecimal getLastPrice();

    /**
     * @return Lowest selling position
     */
    BigDecimal getMinAsk();

    /**
     * @return Highest buying position
     */
    BigDecimal getMaxBid();

    BigDecimal getVolume();

    BigDecimal getPriceVariation24Hours();

    BigDecimal getPriceVariation7Days();

}
