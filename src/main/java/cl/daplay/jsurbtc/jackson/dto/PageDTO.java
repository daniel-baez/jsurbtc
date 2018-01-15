package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.Page;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class PageDTO {

    @JsonProperty("meta")
    private final Page meta;

    @JsonCreator
    public PageDTO(@JsonProperty("meta") Page meta) {
        this.meta = meta;
    }

    public Page getMeta() {
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
