package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.JacksonPage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class PageDTO {

    @JsonProperty("meta")
    private final JacksonPage meta;

    @JsonCreator
    public PageDTO(@JsonProperty("meta") JacksonPage meta) {
        this.meta = meta;
    }

    public JacksonPage getMeta() {
        return meta;
    }
}
