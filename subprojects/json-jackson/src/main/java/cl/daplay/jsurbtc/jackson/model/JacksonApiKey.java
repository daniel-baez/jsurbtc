package cl.daplay.jsurbtc.jackson.model;

import cl.daplay.jsurbtc.model.ApiKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.time.Instant;

@JsonPropertyOrder({"id",
        "name",
        "expiration_time",
        "enabled",
        "expired",
        "last_access_at",
        "secret"})
public class JacksonApiKey implements ApiKey, Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("id")
    private final String id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("expiration_time")
    private final Instant expirationTime;
    @JsonProperty("enabled")
    private final boolean enabled;
    @JsonProperty("expired")
    private final boolean expired;
    @JsonProperty("last_access_at")
    private final Instant lastAccessAt;
    @JsonProperty("secret")
    private final String secret;

    public JacksonApiKey(JacksonApiKey other) {
        this.id = other.id;
        this.name = other.name;
        this.expirationTime = other.expirationTime;
        this.enabled = other.enabled;
        this.expired = other.expired;
        this.lastAccessAt = other.lastAccessAt;
        this.secret = other.secret;
    }

    @JsonCreator
    public JacksonApiKey(@JsonProperty("id") final String id,
                         @JsonProperty("name") final String name,
                         @JsonProperty("expiration_date") final Instant expirationTime,
                         @JsonProperty("enabled") final boolean enabled,
                         @JsonProperty("expired") final boolean expired,
                         @JsonProperty("last_access_at") final Instant lastAccessAt,
                         @JsonProperty("secret") final String secret) {
        this.id = id;
        this.name = name;
        this.expirationTime = expirationTime;
        this.enabled = enabled;
        this.expired = expired;
        this.lastAccessAt = lastAccessAt;
        this.secret = secret;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getExpirationTime() {
        return expirationTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isExpired() {
        return expired;
    }

    public Instant getLastAccessAt() {
        return lastAccessAt;
    }

    public String getSecret() {
        return secret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JacksonApiKey apiKey = (JacksonApiKey) o;

        if (enabled != apiKey.enabled) return false;
        if (expired != apiKey.expired) return false;
        if (id != null ? !id.equals(apiKey.id) : apiKey.id != null) return false;
        if (name != null ? !name.equals(apiKey.name) : apiKey.name != null) return false;
        if (expirationTime != null ? !expirationTime.equals(apiKey.expirationTime) : apiKey.expirationTime != null)
            return false;
        if (lastAccessAt != null ? !lastAccessAt.equals(apiKey.lastAccessAt) : apiKey.lastAccessAt != null)
            return false;
        return secret != null ? secret.equals(apiKey.secret) : apiKey.secret == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (expirationTime != null ? expirationTime.hashCode() : 0);
        result = 31 * result + (enabled ? 1 : 0);
        result = 31 * result + (expired ? 1 : 0);
        result = 31 * result + (lastAccessAt != null ? lastAccessAt.hashCode() : 0);
        result = 31 * result + (secret != null ? secret.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "APIKey{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", expirationTime=" + expirationTime +
                ", enabled=" + enabled +
                ", expired=" + expired +
                ", lastAccessAt=" + lastAccessAt +
                ", secret='" + secret + '\'' +
                '}';
    }
}
