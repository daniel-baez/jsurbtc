package cl.daplay.jsurbtc.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;

public final class APIKey implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("type")
    private final String id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("expiration_date")
    private final Instant expirationDate;
    @JsonProperty("enabled")
    private final boolean enabled;
    @JsonProperty("expired")
    private final boolean expired;
    @JsonProperty("last_access_at")
    private final Instant lastAccessAt;
    @JsonProperty("secret")
    private final String secret;

    public APIKey(@JsonProperty("type") final String id,
                  @JsonProperty("name") final String name,
                  @JsonProperty("expiration_date") final Instant expirationDate,
                  @JsonProperty("enabled") final boolean enabled,
                  @JsonProperty("expired") final boolean expired,
                  @JsonProperty("last_access_at") final Instant lastAccessAt,
                  @JsonProperty("secret") final String secret) {
        this.id = id;
        this.name = name;
        this.expirationDate = expirationDate;
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

    public Instant getExpirationDate() {
        return expirationDate;
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

        APIKey apiKey = (APIKey) o;

        if (enabled != apiKey.enabled) return false;
        if (expired != apiKey.expired) return false;
        if (id != null ? !id.equals(apiKey.id) : apiKey.id != null) return false;
        if (name != null ? !name.equals(apiKey.name) : apiKey.name != null) return false;
        if (expirationDate != null ? !expirationDate.equals(apiKey.expirationDate) : apiKey.expirationDate != null)
            return false;
        if (lastAccessAt != null ? !lastAccessAt.equals(apiKey.lastAccessAt) : apiKey.lastAccessAt != null)
            return false;
        return secret != null ? secret.equals(apiKey.secret) : apiKey.secret == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (expirationDate != null ? expirationDate.hashCode() : 0);
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
                ", expirationDate=" + expirationDate +
                ", enabled=" + enabled +
                ", expired=" + expired +
                ", lastAccessAt=" + lastAccessAt +
                ", secret='" + secret + '\'' +
                '}';
    }
}
