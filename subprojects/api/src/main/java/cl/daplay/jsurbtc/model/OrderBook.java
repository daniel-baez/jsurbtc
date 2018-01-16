package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface OrderBook extends Serializable {

    List<Offer> getBids();

    List<Offer> getAsks();

    interface Offer extends Serializable {

        BigDecimal getPrice();

        BigDecimal getAmount();
    }
}
