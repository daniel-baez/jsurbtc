package cl.daplay.jsurbtc.dto;

import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@JsonPropertyOrder({ "withdrawals", "meta" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class WithdrawalsDTO {

    @JsonProperty("withdrawals")
    private final List<Withdrawal> withdrawals;
    @JsonProperty("meta")
    private final PaginationDTO pagination;

    @JsonCreator
    public WithdrawalsDTO(@JsonProperty("orders") List<Withdrawal> orders,
                          @JsonProperty("meta") PaginationDTO pagination) {
        this.withdrawals = orders;
        this.pagination = pagination;
    }

    public PaginationDTO getPagination() {
        return pagination;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

}
