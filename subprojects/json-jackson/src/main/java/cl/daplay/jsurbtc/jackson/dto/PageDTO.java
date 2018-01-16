package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.JacksonPage;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageDTO pageDTO = (PageDTO) o;
        return Objects.equals(meta, pageDTO.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(meta);
    }

}
