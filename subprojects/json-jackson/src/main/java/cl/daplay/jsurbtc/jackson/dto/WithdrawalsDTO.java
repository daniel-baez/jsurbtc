package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.JacksonPage;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({ "withdrawals", "meta" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class WithdrawalsDTO {

    @JsonProperty("withdrawals")
    private final List<Withdrawal> withdrawals;
    @JsonProperty("meta")
    private final JacksonPage pagination;

    @JsonCreator
    public WithdrawalsDTO(@JsonProperty("orders") List<Withdrawal> orders,
                          @JsonProperty("meta") JacksonPage pagination) {
        this.withdrawals = orders;
        this.pagination = pagination;
    }

    public JacksonPage getPagination() {
        return pagination;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final WithdrawalsDTO that = (WithdrawalsDTO) o;

        if (withdrawals != null ? !withdrawals.equals(that.withdrawals) : that.withdrawals != null) return false;
        return pagination != null ? pagination.equals(that.pagination) : that.pagination == null;
    }

    @Override
    public int hashCode() {
        int result = withdrawals != null ? withdrawals.hashCode() : 0;
        result = 31 * result + (pagination != null ? pagination.hashCode() : 0);
        return result;
    }
}
