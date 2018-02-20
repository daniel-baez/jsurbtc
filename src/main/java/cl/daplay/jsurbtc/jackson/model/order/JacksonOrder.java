package cl.daplay.jsurbtc.jackson.model.order;

import cl.daplay.jsurbtc.jackson.model.JacksonAmount;
import cl.daplay.jsurbtc.model.Order;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JacksonOrder implements Order, Serializable {

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
    private String marketId;
    @JsonProperty("account_id")
    private long accountId;
    @JsonProperty("fee_currency")
    private String feeCurrency;
    @JsonProperty("price_type")
    private String priceType;
    @JsonProperty("limit")
    private JacksonAmount limit;
    @JsonProperty("amount")
    private JacksonAmount amount;
    @JsonProperty("original_amount")
    private JacksonAmount originalAmount;
    @JsonProperty("traded_amount")
    private JacksonAmount tradedAmount;
    @JsonProperty("total_exchanged")
    private JacksonAmount totalExchanged;
    @JsonProperty("paid_fee")
    private JacksonAmount paidFee;

    @JsonCreator
    public JacksonOrder(@JsonProperty("id") long id,
                        @JsonProperty("type") String type,
                        @JsonProperty("state") String state,
                        @JsonProperty("created_at") Instant createdAt,
                        @JsonProperty("market_id") String marketId,
                        @JsonProperty("account_id") long accountId,
                        @JsonProperty("fee_currency") String feeCurrency,
                        @JsonProperty("price_type") String priceType,
                        @JsonProperty("limit") JacksonAmount limit,
                        @JsonProperty("amount") JacksonAmount amount,
                        @JsonProperty("original_amount") JacksonAmount originalAmount,
                        @JsonProperty("traded_amount") JacksonAmount tradedAmount,
                        @JsonProperty("total_exchanged") JacksonAmount totalExchanged,
                        @JsonProperty("paid_fee") JacksonAmount paidFee) {
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

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public long getAccountId() {
        return accountId;
    }

    @Override
    public String getFeeCurrency() {
        return feeCurrency;
    }

    @Override
    public String getPriceType() {
        return priceType;
    }

    @Override
    public BigDecimal getLimit() {
        return limit.getAmount();
    }

    @Override
    public String getLimitCurrency() {
        return limit.getCurrency();
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
    public BigDecimal getOriginalAmount() {
        return originalAmount.getAmount();
    }

    @Override
    public String getOriginalAmountCurrency() {
        return originalAmount.getCurrency();
    }

    @Override
    public BigDecimal getTradedAmount() {
        return tradedAmount.getAmount();
    }

    @Override
    public String getTradedAmountCurrency() {
        return tradedAmount.getCurrency();
    }

    @Override
    public BigDecimal getTotalExchanged() {
        return totalExchanged.getAmount();
    }

    @Override
    public String getTotalExchangedCurrency() {
        return totalExchanged.getCurrency();
    }

    @Override
    public BigDecimal getPaidFee() {
        return paidFee.getAmount();
    }

    @Override
    public String getPaidFeeCurrency() {
        return paidFee.getCurrency();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JacksonOrder order = (JacksonOrder) o;

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
        result = 31 * result + (marketId != null ? marketId.hashCode() : 0);
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
