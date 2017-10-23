package cl.daplay.jsurbtc.model.deposit;

import cl.daplay.jsurbtc.model.Amount;
import cl.daplay.jsurbtc.model.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class Deposit implements Serializable {

    private static final long serialVersionUID = 2017_10_22;

    @JsonProperty("id")
    private final long id;
    @JsonProperty("state")
    private final String state;
    @JsonProperty("currency")
    private final Currency currency;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("deposit_data")
    private final DepositData depositData;
    @JsonProperty("amount")
    private final Amount amount;
    @JsonProperty("fee")
    private final Amount fee;

    @JsonCreator
    public Deposit(@JsonProperty("id") final long id,
                   @JsonProperty("state") final String state,
                   @JsonProperty("currency") final Currency currency,
                   @JsonProperty("created_at") final Instant createdAt,
                   @JsonProperty("deposit_data") final DepositData depositData,
                   @JsonProperty("amount") final Amount amount,
                   @JsonProperty("fee") final Amount fee) {
        this.id = id;
        this.state = state;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = createdAt;
        this.depositData = depositData;
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public Amount getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public DepositData getDepositData() {
        return depositData;
    }

    public Amount getFee() {
        return fee;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Deposit deposit = (Deposit) o;

        if (id != deposit.id) return false;
        if (state != null ? !state.equals(deposit.state) : deposit.state != null) return false;
        if (currency != deposit.currency) return false;
        if (createdAt != null ? !createdAt.equals(deposit.createdAt) : deposit.createdAt != null) return false;
        if (depositData != null ? !depositData.equals(deposit.depositData) : deposit.depositData != null) return false;
        if (amount != null ? !amount.equals(deposit.amount) : deposit.amount != null) return false;
        return fee != null ? fee.equals(deposit.fee) : deposit.fee == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (depositData != null ? depositData.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", currency=" + currency +
                ", createdAt=" + createdAt +
                ", depositData=" + depositData +
                ", amount=" + amount +
                ", fee=" + fee +
                '}';
    }
}
