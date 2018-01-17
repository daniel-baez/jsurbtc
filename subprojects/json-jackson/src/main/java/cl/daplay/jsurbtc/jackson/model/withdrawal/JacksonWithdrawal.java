package cl.daplay.jsurbtc.jackson.model.withdrawal;

import cl.daplay.jsurbtc.jackson.model.JacksonAmount;
import cl.daplay.jsurbtc.model.Withdrawal;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public class JacksonWithdrawal implements Withdrawal, Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("id")
    private final long id;
    @JsonProperty("state")
    private final String state;
    @JsonProperty("currency")
    private final String currency;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("withdrawal_data")
    private final JacksonWithdrawalData withdrawalWithdrawalData;
    @JsonProperty("amount")
    private final JacksonAmount amount;
    @JsonProperty("fee")
    private final JacksonAmount fee;

    public JacksonWithdrawal(JacksonWithdrawal other) {
        this.id = other.id;
        this.state = other.state;
        this.currency = other.currency;
        this.createdAt = other.createdAt;
        this.withdrawalWithdrawalData = other.withdrawalWithdrawalData;
        this.amount = other.amount;
        this.fee = other.fee;
    }

    public JacksonWithdrawal(@JsonProperty("id") long id,
                             @JsonProperty("state") String state,
                             @JsonProperty("currency") String currency,
                             @JsonProperty("created_at") Instant createdAt,
                             @JsonProperty("withdrawal_data") JacksonWithdrawalData withdrawalWithdrawalData,
                             @JsonProperty("amount") JacksonAmount amount,
                             @JsonProperty("fee") JacksonAmount fee) {
        this.id = id;
        this.state = state;
        this.currency = currency;
        this.createdAt = createdAt;
        this.withdrawalWithdrawalData = withdrawalWithdrawalData;
        this.amount = amount;
        this.fee = fee;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getCurrency() {
        return currency;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public JacksonWithdrawalData getWithdrawalData() {
        return withdrawalWithdrawalData;
    }

    @Override
    public BigDecimal getAmount() {
        return amount.getAmount();
    }

    @Override
    public String getAmountCurrency() {
        return amount.getCurrency();
    }

    @Override
    public BigDecimal getFee() {
        return fee.getAmount();
    }

    @Override
    public String getFeeCurrency() {
        return fee.getCurrency();
    }

    @Override
    public String toString() {
        return "Withdrawal{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", currency=" + currency +
                ", createdAt=" + createdAt +
                ", withdrawalWithdrawalData=" + withdrawalWithdrawalData +
                ", amount=" + amount +
                ", fee=" + fee +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JacksonWithdrawal that = (JacksonWithdrawal) o;

        if (id != that.id) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (withdrawalWithdrawalData != null ? !withdrawalWithdrawalData.equals(that.withdrawalWithdrawalData) : that.withdrawalWithdrawalData != null)
            return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return fee != null ? fee.equals(that.fee) : that.fee == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (withdrawalWithdrawalData != null ? withdrawalWithdrawalData.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        return result;
    }
}
