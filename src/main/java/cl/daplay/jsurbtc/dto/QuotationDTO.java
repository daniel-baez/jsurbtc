package cl.daplay.jsurbtc.dto;

import cl.daplay.jsurbtc.model.market.MarketID;
import cl.daplay.jsurbtc.model.quotation.Quotation;
import cl.daplay.jsurbtc.model.quotation.QuotationType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement
public class QuotationDTO {

    @JsonProperty("quotation")
    private Quotation quotation;

    @JsonCreator
    public QuotationDTO(@JsonProperty("quotation") final Quotation quotation) {
        this.quotation = quotation;
    }

    public Quotation getQuotation() {
        return quotation;
    }
}
