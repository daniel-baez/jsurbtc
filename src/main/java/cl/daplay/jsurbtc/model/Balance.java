package cl.daplay.jsurbtc.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonPropertyOrder({ "id", "amount", "available_amount", "frozen_amount", "pending_withdraw_amount", "account_id" })
public final class Balance implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("account_id")
    private final long accountId;
    @JsonProperty("id")
    private final Currency id;
    @JsonProperty("amount")
    private final Amount amount;
    @JsonProperty("available_amount")
    private final Amount availableAmount;
    @JsonProperty("frozen_amount")
    private final Amount frozenAmount;
    @JsonProperty("pending_withdraw_amount")
    private final Amount pendingWithdrawAmount;

    @JsonCreator
    public Balance(@JsonProperty("account_id") final long accountId,
                   @JsonProperty("id") final Currency id,
                   @JsonProperty("amount") final Amount amount,
                   @JsonProperty("available_amount") final Amount availableAmount,
                   @JsonProperty("frozen_amount") final Amount frozenAmount,
                   @JsonProperty("pending_withdraw_amount") final Amount pendingWithdrawAmount) {
        this.accountId = accountId;
        this.id = id;
        this.amount = amount;
        this.availableAmount = availableAmount;
        this.frozenAmount = frozenAmount;
        this.pendingWithdrawAmount = pendingWithdrawAmount;
    }

    public long getAccountId() {
        return accountId;
    }

    public Currency getId() {
        return id;
    }

    public Amount getAmount() {
        return amount;
    }

    public Amount getAvailableAmount() {
        return availableAmount;
    }

    public Amount getFrozenAmount() {
        return frozenAmount;
    }

    public Amount getPendingWithdrawAmount() {
        return pendingWithdrawAmount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "accountId=" + accountId +
                ", id='" + id + '\'' +
                ", amount=" + amount +
                ", availableAmount=" + availableAmount +
                ", frozenAmount=" + frozenAmount +
                ", pendingWithdrawAmount=" + pendingWithdrawAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        if (accountId != balance.accountId) return false;
        if (id != null ? !id.equals(balance.id) : balance.id != null) return false;
        if (amount != null ? !amount.equals(balance.amount) : balance.amount != null) return false;
        if (availableAmount != null ? !availableAmount.equals(balance.availableAmount) : balance.availableAmount != null)
            return false;
        if (frozenAmount != null ? !frozenAmount.equals(balance.frozenAmount) : balance.frozenAmount != null)
            return false;
        return pendingWithdrawAmount != null ? pendingWithdrawAmount.equals(balance.pendingWithdrawAmount) : balance.pendingWithdrawAmount == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (availableAmount != null ? availableAmount.hashCode() : 0);
        result = 31 * result + (frozenAmount != null ? frozenAmount.hashCode() : 0);
        result = 31 * result + (pendingWithdrawAmount != null ? pendingWithdrawAmount.hashCode() : 0);
        return result;
    }
}
