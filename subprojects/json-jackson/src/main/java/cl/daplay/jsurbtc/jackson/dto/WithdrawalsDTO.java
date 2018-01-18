package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.withdrawal.JacksonWithdrawal;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonPropertyOrder({ "withdrawals", "meta" })
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public final class WithdrawalsDTO {

    @JsonProperty("withdrawals")
    private final List<JacksonWithdrawal> withdrawals;

    @JsonCreator
    public WithdrawalsDTO(@JsonProperty("orders") List<JacksonWithdrawal> orders) {
        this.withdrawals = orders;
    }

    public List<JacksonWithdrawal> getWithdrawals() {
        return withdrawals;
    }
}
