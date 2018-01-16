package cl.daplay.jsurbtc.model.deposit;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

public interface DepositData extends Serializable {

    String getType();

    Optional<String> getAddress();

    Optional<String> getTxHash();

    Optional<Instant> getCreatedAt();

    Optional<Instant> getUpdatedAt();

    Optional<String> getUploadUrl();
}
