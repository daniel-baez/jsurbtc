package cl.daplay.jsurbtc.model.deposit;

import cl.daplay.jsurbtc.Utils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public final class DepositData implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("type")
    private final String type;
    @JsonProperty("address")
    private final String address;
    @JsonProperty("tx_hash")
    private final String txHash;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("updated_at")
    private final Instant updatedAt;

    @JsonCreator
    public DepositData(@JsonProperty("type") String type,
                       @JsonProperty("address") String address,
                       @JsonProperty("tx_hash") String txHash,
                       @JsonProperty("created_at") Instant createdAt,
                       @JsonProperty("updated_at") Instant updatedAt) {
        this.type = type;
        this.address = address;
        this.txHash = txHash;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getType() {
        return type;
    }

    public Optional<String> getAddress() {
        return Optional.ofNullable(address).filter(Utils::isNotEmpty);
    }

    public Optional<String> getTxHash() {
        return Optional.ofNullable(txHash).filter(Utils::isNotEmpty);
    }

    public Optional<Instant> getCreatedAt() {
        return Optional.ofNullable(createdAt);
    }

    public Optional<Instant> getUpdatedAt() {
        return Optional.ofNullable(updatedAt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepositData that = (DepositData) o;

        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (txHash != null ? !txHash.equals(that.txHash) : that.txHash != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        return updatedAt != null ? updatedAt.equals(that.updatedAt) : that.updatedAt == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (txHash != null ? txHash.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DepositData{" +
                "type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", txHash='" + txHash + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
