package cl.daplay.jsurbtc.model.quotation;

import cl.daplay.jsurbtc.model.Amount;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public final class Quotation implements Serializable {

    private static final long serialVersionUID = 2017_08_12;

    @JsonProperty("amount")
    private final Amount amount;
    @JsonProperty("limit")
    private final Amount limit;
    @JsonProperty("type")
    private final QuotationType type;
    @JsonProperty("order_amount")
    private final Amount orderAmount;
    @JsonProperty("base_exchanged")
    private final Amount baseExchanged;
    @JsonProperty("quote_exchanged")
    private final Amount quoteExchanged;
    @JsonProperty("base_balance_change")
    private final Amount baseBalanceChange;
    @JsonProperty("quote_balance_change")
    private final Amount quoteBalanceChange;
    @JsonProperty("fee")
    private final Amount fee;
    @JsonProperty("incomplete")
    private final boolean incomplete;

    @JsonCreator
    public Quotation(@JsonProperty("amount") Amount amount,
                     @JsonProperty("limit") Amount limit,
                     @JsonProperty("type") QuotationType type,
                     @JsonProperty("order_amount") Amount orderAmount,
                     @JsonProperty("base_exchanged") Amount baseExchanged,
                     @JsonProperty("quote_exchanged") Amount quoteExchanged,
                     @JsonProperty("base_balance_change") Amount baseBalanceChange,
                     @JsonProperty("quote_balance_change") Amount quoteBalanceChange,
                     @JsonProperty("fee") Amount fee,
                     @JsonProperty("incomplete") boolean incomplete) {
        this.amount = amount;
        this.limit = limit;
        this.type = type;
        this.orderAmount = orderAmount;
        this.baseExchanged = baseExchanged;
        this.quoteExchanged = quoteExchanged;
        this.baseBalanceChange = baseBalanceChange;
        this.quoteBalanceChange = quoteBalanceChange;
        this.fee = fee;
        this.incomplete = incomplete;
    }

    public Amount getAmount() {
        return amount;
    }

    public Amount getLimit() {
        return limit;
    }

    public QuotationType getType() {
        return type;
    }

    public Amount getOrderAmount() {
        return orderAmount;
    }

    public Amount getBaseExchanged() {
        return baseExchanged;
    }

    public Amount getQuoteExchanged() {
        return quoteExchanged;
    }

    public Amount getBaseBalanceChange() {
        return baseBalanceChange;
    }

    public Amount getQuoteBalanceChange() {
        return quoteBalanceChange;
    }

    public Amount getFee() {
        return fee;
    }

    public boolean isIncomplete() {
        return incomplete;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Quotation quotation = (Quotation) o;

        if (incomplete != quotation.incomplete) return false;
        if (amount != null ? !amount.equals(quotation.amount) : quotation.amount != null) return false;
        if (limit != null ? !limit.equals(quotation.limit) : quotation.limit != null) return false;
        if (type != quotation.type) return false;
        if (orderAmount != null ? !orderAmount.equals(quotation.orderAmount) : quotation.orderAmount != null)
            return false;
        if (baseExchanged != null ? !baseExchanged.equals(quotation.baseExchanged) : quotation.baseExchanged != null)
            return false;
        if (quoteExchanged != null ? !quoteExchanged.equals(quotation.quoteExchanged) : quotation.quoteExchanged != null)
            return false;
        if (baseBalanceChange != null ? !baseBalanceChange.equals(quotation.baseBalanceChange) : quotation.baseBalanceChange != null)
            return false;
        if (quoteBalanceChange != null ? !quoteBalanceChange.equals(quotation.quoteBalanceChange) : quotation.quoteBalanceChange != null)
            return false;
        return fee != null ? fee.equals(quotation.fee) : quotation.fee == null;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (orderAmount != null ? orderAmount.hashCode() : 0);
        result = 31 * result + (baseExchanged != null ? baseExchanged.hashCode() : 0);
        result = 31 * result + (quoteExchanged != null ? quoteExchanged.hashCode() : 0);
        result = 31 * result + (baseBalanceChange != null ? baseBalanceChange.hashCode() : 0);
        result = 31 * result + (quoteBalanceChange != null ? quoteBalanceChange.hashCode() : 0);
        result = 31 * result + (fee != null ? fee.hashCode() : 0);
        result = 31 * result + (incomplete ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Quotation{" +
                "amount=" + amount +
                ", limit=" + limit +
                ", type=" + type +
                ", orderAmount=" + orderAmount +
                ", baseExchanged=" + baseExchanged +
                ", quoteExchanged=" + quoteExchanged +
                ", baseBalanceChange=" + baseBalanceChange +
                ", quoteBalanceChange=" + quoteBalanceChange +
                ", fee=" + fee +
                ", incomplete=" + incomplete +
                '}';
    }

}
