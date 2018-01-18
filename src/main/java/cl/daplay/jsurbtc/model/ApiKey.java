package cl.daplay.jsurbtc.model;

import java.io.Serializable;
import java.time.Instant;

public interface ApiKey extends Serializable {

    String getId();

    String getName();

    Instant getExpirationTime();

    boolean isEnabled();

    boolean isExpired();

    Instant getLastAccessAt();

    String getSecret();

}
