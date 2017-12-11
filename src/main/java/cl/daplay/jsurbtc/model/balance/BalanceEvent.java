package cl.daplay.jsurbtc.model.balance;

import cl.daplay.jsurbtc.model.Amount;
import cl.daplay.jsurbtc.model.Currency;
import cl.daplay.jsurbtc.model.order.OrderType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;

public class BalanceEvent implements Serializable {

    private static final long serialVersionUID = 2017_08_12;

    @JsonProperty("id")
    private final long id;
    @JsonProperty("currency")
    private final Currency currency;
    @JsonProperty("event")
    private final BalanceEventType type;
    @JsonProperty("account_id")
    private final long accountId;
    @JsonProperty("event_ids")
    private final long[] eventIds;
    @JsonProperty("transaction_type")
    private final OrderType transactionType;
    @JsonProperty("old_amount")
    private final BigDecimal oldAmount;
    @JsonProperty("new_amount")
    private final BigDecimal newAmount;
    @JsonProperty("old_frozen_amount")
    private final BigDecimal oldFrozenAmount;
    @JsonProperty("new_frozen_amount")
    private final BigDecimal newFrozenAmount;
    @JsonProperty("old_pending_withdraw_amount")
    private final BigDecimal oldPendingWithdrawAmount;
    @JsonProperty("new_pending_withdraw_amount")
    private final BigDecimal newPendingWithdrawAmount;
    @JsonProperty("old_frozen_for_fee")
    private final BigDecimal oldFrozenForFee;
    @JsonProperty("new_frozen_for_fee")
    private final BigDecimal newFrozenForFee;
    @JsonProperty("old_available_amount")
    private final BigDecimal oldAvailableAmount;
    @JsonProperty("new_available_amount")
    private final BigDecimal newAvailableAmount;
    @JsonProperty("created_at")
    private final Instant createdAt;
    @JsonProperty("transfer_description")
    private final String transferDescription;

    public BalanceEvent(BalanceEvent other) {
        this.id = other.id;
        this.currency = other.currency;
        this.type = other.type;
        this.accountId = other.accountId;
        this.eventIds = other.eventIds;
        this.transactionType = other.transactionType;
        this.oldAmount = other.oldAmount;
        this.newAmount = other.newAmount;
        this.oldFrozenAmount = other.oldFrozenAmount;
        this.newFrozenAmount = other.newFrozenAmount;
        this.oldPendingWithdrawAmount = other.oldPendingWithdrawAmount;
        this.newPendingWithdrawAmount = other.newPendingWithdrawAmount;
        this.oldFrozenForFee = other.oldFrozenForFee;
        this.newFrozenForFee = other.newFrozenForFee;
        this.oldAvailableAmount = other.oldAvailableAmount;
        this.newAvailableAmount = other.newAvailableAmount;
        this.createdAt = other.createdAt;
        this.transferDescription = other.transferDescription;
    }

    public BalanceEvent(@JsonProperty("id") final long id,
                        @JsonProperty("currency") final Currency currency,
                        @JsonProperty("event") final BalanceEventType type,
                        @JsonProperty("account_id") final long accountId,
                        @JsonProperty("event_ids") final long[] eventIds,
                        @JsonProperty("transaction_type") final OrderType transactionType,
                        @JsonProperty("old_amount") final BigDecimal oldAmount,
                        @JsonProperty("new_amount") final BigDecimal newAmount,
                        @JsonProperty("old_frozen_amount") final BigDecimal oldFrozenAmount,
                        @JsonProperty("new_frozen_amount") final BigDecimal newFrozenAmount,
                        @JsonProperty("old_pending_withdraw_amount") final BigDecimal oldPendingWithdrawAmount,
                        @JsonProperty("new_pending_withdraw_amount") final BigDecimal newPendingWithdrawAmount,
                        @JsonProperty("old_frozen_for_fee") final BigDecimal oldFrozenForFee,
                        @JsonProperty("new_frozen_for_fee") final BigDecimal newFrozenForFee,
                        @JsonProperty("old_available_amount") final BigDecimal oldAvailableAmount,
                        @JsonProperty("new_available_amount") final BigDecimal newAvailableAmount,
                        @JsonProperty("created_at") final Instant createdAt,
                        @JsonProperty("transfer_description") final String transferDescription) {
        this.id = id;
        this.currency = currency;
        this.type = type;
        this.accountId = accountId;
        this.eventIds = eventIds;
        this.transactionType = transactionType;
        this.oldAmount = oldAmount;
        this.newAmount = newAmount;
        this.oldFrozenAmount = oldFrozenAmount;
        this.newFrozenAmount = newFrozenAmount;
        this.oldPendingWithdrawAmount = oldPendingWithdrawAmount;
        this.newPendingWithdrawAmount = newPendingWithdrawAmount;
        this.oldFrozenForFee = oldFrozenForFee;
        this.newFrozenForFee = newFrozenForFee;
        this.oldAvailableAmount = oldAvailableAmount;
        this.newAvailableAmount = newAvailableAmount;
        this.createdAt = createdAt;
        this.transferDescription = transferDescription;
    }

    public long getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public BalanceEventType getType() {
        return type;
    }

    public long getAccountId() {
        return accountId;
    }

    public long[] getEventIds() {
        return eventIds;
    }

    public OrderType getTransactionType() {
        return transactionType;
    }

    /**
     * @return newAmount - oldAmount
     */
    @JsonIgnore
    public Amount getDeltaAmount() {
        return new Amount(currency, getNewAmount().subtract(getOldAmount()));
    }

    @JsonIgnore
    public BigDecimal getOldAmount() {
        return adjustToCurrencyUnits(oldAmount);
    }

    @JsonIgnore
    public BigDecimal getNewAmount() {
        return adjustToCurrencyUnits(newAmount);
    }

    @JsonIgnore
    public BigDecimal getOldFrozenAmount() {
        return adjustToCurrencyUnits(oldFrozenAmount);
    }

    @JsonIgnore
    public BigDecimal getNewFrozenAmount() {
        return adjustToCurrencyUnits(newFrozenAmount);
    }

    @JsonIgnore
    public BigDecimal getOldPendingWithdrawAmount() {
        return adjustToCurrencyUnits(oldPendingWithdrawAmount);
    }

    @JsonIgnore
    public BigDecimal getNewPendingWithdrawAmount() {
        return adjustToCurrencyUnits(newPendingWithdrawAmount);
    }

    @JsonIgnore
    public BigDecimal getOldFrozenForFee() {
        return adjustToCurrencyUnits(oldFrozenForFee);
    }

    @JsonIgnore
    public BigDecimal getNewFrozenForFee() {
        return adjustToCurrencyUnits(newFrozenForFee);
    }

    @JsonIgnore
    public BigDecimal getOldAvailableAmount() {
        return adjustToCurrencyUnits(oldAvailableAmount);
    }

    @JsonIgnore
    public BigDecimal getNewAvailableAmount() {
        return adjustToCurrencyUnits(newAvailableAmount);
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getTransferDescription() {
        return transferDescription;
    }

    private BigDecimal adjustToCurrencyUnits(final BigDecimal amount) {
        return amount == null ? null : amount.divide(currency.getSubunitsToUnit());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BalanceEvent that = (BalanceEvent) o;

        if (id != that.id) return false;
        if (accountId != that.accountId) return false;
        if (currency != that.currency) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (!Arrays.equals(eventIds, that.eventIds)) return false;
        if (transactionType != that.transactionType) return false;
        if (oldAmount != null ? !oldAmount.equals(that.oldAmount) : that.oldAmount != null) return false;
        if (newAmount != null ? !newAmount.equals(that.newAmount) : that.newAmount != null) return false;
        if (oldFrozenAmount != null ? !oldFrozenAmount.equals(that.oldFrozenAmount) : that.oldFrozenAmount != null)
            return false;
        if (newFrozenAmount != null ? !newFrozenAmount.equals(that.newFrozenAmount) : that.newFrozenAmount != null)
            return false;
        if (oldPendingWithdrawAmount != null ? !oldPendingWithdrawAmount.equals(that.oldPendingWithdrawAmount) : that.oldPendingWithdrawAmount != null)
            return false;
        if (newPendingWithdrawAmount != null ? !newPendingWithdrawAmount.equals(that.newPendingWithdrawAmount) : that.newPendingWithdrawAmount != null)
            return false;
        if (oldFrozenForFee != null ? !oldFrozenForFee.equals(that.oldFrozenForFee) : that.oldFrozenForFee != null)
            return false;
        if (newFrozenForFee != null ? !newFrozenForFee.equals(that.newFrozenForFee) : that.newFrozenForFee != null)
            return false;
        if (oldAvailableAmount != null ? !oldAvailableAmount.equals(that.oldAvailableAmount) : that.oldAvailableAmount != null)
            return false;
        if (newAvailableAmount != null ? !newAvailableAmount.equals(that.newAvailableAmount) : that.newAvailableAmount != null)
            return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        return transferDescription != null ? transferDescription.equals(that.transferDescription) : that.transferDescription == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + Arrays.hashCode(eventIds);
        result = 31 * result + (transactionType != null ? transactionType.hashCode() : 0);
        result = 31 * result + (oldAmount != null ? oldAmount.hashCode() : 0);
        result = 31 * result + (newAmount != null ? newAmount.hashCode() : 0);
        result = 31 * result + (oldFrozenAmount != null ? oldFrozenAmount.hashCode() : 0);
        result = 31 * result + (newFrozenAmount != null ? newFrozenAmount.hashCode() : 0);
        result = 31 * result + (oldPendingWithdrawAmount != null ? oldPendingWithdrawAmount.hashCode() : 0);
        result = 31 * result + (newPendingWithdrawAmount != null ? newPendingWithdrawAmount.hashCode() : 0);
        result = 31 * result + (oldFrozenForFee != null ? oldFrozenForFee.hashCode() : 0);
        result = 31 * result + (newFrozenForFee != null ? newFrozenForFee.hashCode() : 0);
        result = 31 * result + (oldAvailableAmount != null ? oldAvailableAmount.hashCode() : 0);
        result = 31 * result + (newAvailableAmount != null ? newAvailableAmount.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (transferDescription != null ? transferDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BalanceEvent{" +
                "id=" + id +
                ", currency=" + currency +
                ", type='" + type + '\'' +
                ", accountId=" + accountId +
                ", eventIds=" + Arrays.toString(eventIds) +
                ", transactionType=" + transactionType +
                ", oldAmount=" + getOldAmount() +
                ", newAmount=" + getNewAmount() +
                ", deltaAmount=" + getDeltaAmount() +
                ", oldFrozenAmount=" + getOldFrozenAmount() +
                ", newFrozenAmount=" + getNewFrozenAmount() +
                ", oldPendingWithdrawAmount=" + getOldPendingWithdrawAmount() +
                ", newPendingWithdrawAmount=" + getNewPendingWithdrawAmount() +
                ", oldFrozenForFee=" + getOldFrozenForFee() +
                ", newFrozenForFee=" + getNewFrozenForFee() +
                ", oldAvailableAmount=" + getOldAvailableAmount() +
                ", newAvailableAmount=" + getNewAvailableAmount() +
                ", createdAt=" + createdAt +
                ", transferDescription='" + transferDescription + '\'' +
                '}';
    }
}
