package cl.daplay.jsurbtc.model.order;

import java.io.Serializable;
import java.math.BigDecimal;

public interface Offer extends Serializable {

    BigDecimal getPrice();

    BigDecimal getAmount();
}

