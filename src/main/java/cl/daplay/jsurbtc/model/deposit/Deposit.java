package cl.daplay.jsurbtc.model.deposit;

import cl.daplay.jsurbtc.model.Amount;
import cl.daplay.jsurbtc.model.Currency;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Instant;

public final class Deposit implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("id")
    private final long id;
    @JsonProperty("state")
    private final String state;
    @JsonProperty("amount")
    private final Amount amount;
    @JsonProperty("currency")
    private final Currency currency;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("deposit_data")
    private final DepositData depositData;

    public Deposit(@JsonProperty("id") long id,
                   @JsonProperty("state") String state,
                   @JsonProperty("amount") Amount amount,
                   @JsonProperty("currency") Currency currency,
                   @JsonProperty("created_at") Instant createdAt,
                   @JsonProperty("deposit_data") DepositData depositData) {
        this.id = id;
        this.state = state;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = createdAt;
        this.depositData = depositData;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deposit deposit = (Deposit) o;

        if (id != deposit.id) return false;
        if (state != null ? !state.equals(deposit.state) : deposit.state != null) return false;
        if (amount != null ? !amount.equals(deposit.amount) : deposit.amount != null) return false;
        if (currency != deposit.currency) return false;
        if (createdAt != null ? !createdAt.equals(deposit.createdAt) : deposit.createdAt != null) return false;
        return depositData != null ? depositData.equals(deposit.depositData) : deposit.depositData == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (depositData != null ? depositData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", amount=" + amount +
                ", currency=" + currency +
                ", createdAt=" + createdAt +
                ", depositData=" + depositData +
                '}';
    }
}
