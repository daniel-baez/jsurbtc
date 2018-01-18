package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.deposit.JacksonDeposit;
import com.fasterxml.jackson.annotation.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({ "deposits", "meta" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DepositsDTO {

    @JsonProperty("deposits")
    private final List<JacksonDeposit> deposits;

    @JsonCreator
    public DepositsDTO(@JsonProperty("deposits") List<JacksonDeposit> deposits) {
        this.deposits = deposits;
    }

    public List<JacksonDeposit> getDeposits() {
        return deposits;
    }

}
