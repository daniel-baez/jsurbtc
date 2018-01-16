package cl.daplay.jsurbtc.model.trades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public interface Transaction extends Serializable, Comparable<Transaction> {

    Instant getTimestamp();

    BigDecimal getAmount();

    BigDecimal getPrice();

    String getDirection();

}


