package cl.daplay.jsurbtc.model.order;

import cl.daplay.jsurbtc.model.Amount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import static java.lang.String.format;

public class Order implements Serializable {

    private static final long serialVersionUID = 2017_08_06;

    @JsonProperty("id")
    private long id;
    @JsonProperty("type")
    private String type;
    @JsonProperty("state")
    private String state;
    @JsonProperty("created_at")
    private Instant createdAt;
    @JsonProperty("market_id")
    private long marketId;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("fee_currency")
    private String feeCurrency;
    @JsonProperty("price_type")
    private String priceType;
    @JsonProperty("limit")
    private Amount limit;
    @JsonProperty("amount")
    private Amount amount;
    @JsonProperty("original_amount")
    private Amount originalAmount;
    @JsonProperty("traded_amount")
    private Amount tradedAmount;
    @JsonProperty("total_exchanged")
    private Amount totalExchanged;
    @JsonProperty("paid_fee")
    private Amount paidFee;

    public Order(Order other) {
        this.id = other.id;
        this.type = other.type;
        this.state = other.state;
        this.createdAt = other.createdAt;
        this.marketId = other.marketId;
        this.accountId = other.accountId;
        this.feeCurrency = other.feeCurrency;
        this.priceType = other.priceType;
        this.limit = other.limit;
        this.amount = other.amount;
        this.originalAmount = other.originalAmount;
        this.tradedAmount = other.tradedAmount;
        this.totalExchanged = other.totalExchanged;
        this.paidFee = other.paidFee;
    }

    @JsonCreator
    public Order(@JsonProperty("id") long id,
                 @JsonProperty("type") String type,
                 @JsonProperty("state") String state,
                 @JsonProperty("created_at") Instant createdAt,
                 @JsonProperty("market_id") long marketId,
                 @JsonProperty("account_id") long accountId,
                 @JsonProperty("fee_currency") String feeCurrency,
                 @JsonProperty("price_type") String priceType,
                 @JsonProperty("limit") Amount limit,
                 @JsonProperty("amount") Amount amount,
                 @JsonProperty("original_amount") Amount originalAmount,
                 @JsonProperty("traded_amount") Amount tradedAmount,
                 @JsonProperty("total_exchanged") Amount totalExchanged,
                 @JsonProperty("paid_fee") Amount paidFee) {
        this.id = id;
        this.type = type;
        this.state = state;
        this.createdAt = createdAt;
        this.marketId = marketId;
        this.accountId = accountId;
        this.feeCurrency = feeCurrency;
        this.priceType = priceType;
        this.limit = limit;
        this.amount = amount;
        this.originalAmount = originalAmount;
        this.tradedAmount = tradedAmount;
        this.totalExchanged = totalExchanged;
        this.paidFee = paidFee;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getFeeCurrency() {
        return feeCurrency;
    }

    public String getPriceType() {
        return priceType;
    }

    public Amount getLimit() {
        return limit;
    }

    public Amount getAmount() {
        return amount;
    }

    public Amount getOriginalAmount() {
        return originalAmount;
    }

    public Amount getTradedAmount() {
        return tradedAmount;
    }

    public Amount getTotalExchanged() {
        return totalExchanged;
    }

    public Amount getPaidFee() {
        return paidFee;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (marketId != order.marketId) return false;
        if (accountId != order.accountId) return false;
        if (type != null ? !type.equals(order.type) : order.type != null) return false;
        if (state != null ? !state.equals(order.state) : order.state != null) return false;
        if (createdAt != null ? !createdAt.equals(order.createdAt) : order.createdAt != null) return false;
        if (feeCurrency != null ? !feeCurrency.equals(order.feeCurrency) : order.feeCurrency != null) return false;
        if (priceType != null ? !priceType.equals(order.priceType) : order.priceType != null) return false;
        if (limit != null ? !limit.equals(order.limit) : order.limit != null) return false;
        if (amount != null ? !amount.equals(order.amount) : order.amount != null) return false;
        if (originalAmount != null ? !originalAmount.equals(order.originalAmount) : order.originalAmount != null)
            return false;
        if (tradedAmount != null ? !tradedAmount.equals(order.tradedAmount) : order.tradedAmount != null) return false;
        if (totalExchanged != null ? !totalExchanged.equals(order.totalExchanged) : order.totalExchanged != null)
            return false;
        return paidFee != null ? paidFee.equals(order.paidFee) : order.paidFee == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (int) (marketId ^ (marketId >>> 32));
        result = 31 * result + (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + (feeCurrency != null ? feeCurrency.hashCode() : 0);
        result = 31 * result + (priceType != null ? priceType.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (originalAmount != null ? originalAmount.hashCode() : 0);
        result = 31 * result + (tradedAmount != null ? tradedAmount.hashCode() : 0);
        result = 31 * result + (totalExchanged != null ? totalExchanged.hashCode() : 0);
        result = 31 * result + (paidFee != null ? paidFee.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", state='" + state + '\'' +
                ", createdAt=" + createdAt +
                ", marketId=" + marketId +
                ", accountId=" + accountId +
                ", feeCurrency=" + feeCurrency +
                ", priceType='" + priceType + '\'' +
                ", limit='" + limit + '\'' +
                ", amount=" + amount +
                ", originalAmount=" + originalAmount +
                ", tradedAmount=" + tradedAmount +
                ", totalExchanged=" + totalExchanged +
                ", paidFee=" + paidFee +
                '}';
    }
}
