package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface Trades extends Serializable, Iterable<Trades.Transaction> {

    Optional<Instant> getTimestamp();

    Optional<Instant> getLastTimestamp();

    List<Transaction> getEntries();

    interface Transaction extends Serializable, Comparable<Transaction> {

        Instant getTimestamp();

        BigDecimal getAmount();

        BigDecimal getPrice();

        String getDirection();

    }
}
