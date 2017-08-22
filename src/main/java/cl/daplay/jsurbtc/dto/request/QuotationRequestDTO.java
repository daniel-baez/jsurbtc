package cl.daplay.jsurbtc.dto.request;

import cl.daplay.jsurbtc.model.quotation.QuotationType;
import cl.daplay.jsurbtc.model.market.MarketID;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class QuotationRequestDTO {

    @JsonProperty("type")
    private final QuotationType quotationType;
    private final String limit;
    private final BigDecimal amount;
    @JsonProperty("market_id")
    private final MarketID marketID;

    public QuotationRequestDTO(final QuotationType type, final BigDecimal amount) {
        this(type, null, amount, null);
    }

    public QuotationRequestDTO(final QuotationType type, final String limit, final BigDecimal amount, final MarketID marketID) {
        this.quotationType = type;
        this.limit = limit;
        this.amount = amount;
        this.marketID = marketID;
    }

}
