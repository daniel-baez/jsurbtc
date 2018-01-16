package cl.daplay.jsurbtc.model.trades;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface Trades extends Serializable, Iterable<Transaction> {

    Optional<Instant> getTimestamp();

    Optional<Instant> getLastTimestamp();

    List<Transaction> getEntries();

}
