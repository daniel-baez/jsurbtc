package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface Trades extends Serializable, Iterable<Trades.Transaction> {

    Instant getTimestamp();

    Instant getLastTimestamp();

    List<Transaction> getEntries();

    interface Transaction extends Serializable, Comparable<Transaction> {

        Instant getTimestamp();

        BigDecimal getAmount();

        BigDecimal getPrice();

        String getDirection();

    }
}
