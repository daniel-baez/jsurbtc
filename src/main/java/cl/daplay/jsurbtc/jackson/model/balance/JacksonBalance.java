package cl.daplay.jsurbtc.jackson.model.balance;

import cl.daplay.jsurbtc.jackson.model.JacksonAmount;
import cl.daplay.jsurbtc.model.Balance;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonPropertyOrder({ "id", "amount", "available_amount", "frozen_amount", "pending_withdraw_amount", "account_id" })
public class JacksonBalance implements Balance, Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("account_id")
    private final long accountId;
    @JsonProperty("id")
    private final String id;
    @JsonProperty("amount")
    private final JacksonAmount amount;
    @JsonProperty("available_amount")
    private final JacksonAmount availableAmount;
    @JsonProperty("frozen_amount")
    private final JacksonAmount frozenAmount;
    @JsonProperty("pending_withdraw_amount")
    private final JacksonAmount pendingWithdrawAmount;

    @JsonCreator
    public JacksonBalance(@JsonProperty("account_id") final long accountId,
                          @JsonProperty("id") final String id,
                          @JsonProperty("amount") final JacksonAmount amount,
                          @JsonProperty("available_amount") final JacksonAmount availableAmount,
                          @JsonProperty("frozen_amount") final JacksonAmount frozenAmount,
                          @JsonProperty("pending_withdraw_amount") final JacksonAmount pendingWithdrawAmount) {
        this.accountId = accountId;
        this.id = id;
        this.amount = amount;
        this.availableAmount = availableAmount;
        this.frozenAmount = frozenAmount;
        this.pendingWithdrawAmount = pendingWithdrawAmount;
    }

    @Override
    public long getAccountId() {
        return accountId;
    }

    @Override
    public String getId() {
        return id;
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
    public BigDecimal getAvailableAmount() {
        return availableAmount.getAmount();
    }

    @Override
    public String getAvailableAmountCurrency() {
        return availableAmount.getCurrency();
    }

    @Override
    public BigDecimal getFrozenAmount() {
        return frozenAmount.getAmount();
    }

    @Override
    public String getFrozenAmountCurrency() {
        return frozenAmount.getCurrency();
    }

    @Override
    public BigDecimal getPendingWithdrawAmount() {
        return pendingWithdrawAmount.getAmount();
    }

    @Override
    public String getPendingWithdrawAmountCurrency() {
        return pendingWithdrawAmount.getCurrency();
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

        JacksonBalance balance = (JacksonBalance) o;

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
