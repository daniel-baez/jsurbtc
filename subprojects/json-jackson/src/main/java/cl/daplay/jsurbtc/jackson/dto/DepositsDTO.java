package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.JacksonPage;
import cl.daplay.jsurbtc.jackson.model.deposit.JacksonDeposit;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
@JsonPropertyOrder({ "deposits", "meta" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class DepositsDTO {

    @JsonProperty("deposits")
    private final List<JacksonDeposit> deposits;
    @JsonProperty("meta")
    private final JacksonPage pagination;

    @JsonCreator
    public DepositsDTO(@JsonProperty("deposits") List<JacksonDeposit> deposits,
                       @JsonProperty("meta") JacksonPage pagination) {
        this.deposits = deposits;
        this.pagination = pagination;
    }

    public JacksonPage getPagination() {
        return pagination;
    }

    public List<JacksonDeposit> getDeposits() {
        return deposits;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final DepositsDTO that = (DepositsDTO) o;

        if (deposits != null ? !deposits.equals(that.deposits) : that.deposits != null) return false;
        return pagination != null ? pagination.equals(that.pagination) : that.pagination == null;
    }

    @Override
    public int hashCode() {
        int result = deposits != null ? deposits.hashCode() : 0;
        result = 31 * result + (pagination != null ? pagination.hashCode() : 0);
        return result;
    }
}
