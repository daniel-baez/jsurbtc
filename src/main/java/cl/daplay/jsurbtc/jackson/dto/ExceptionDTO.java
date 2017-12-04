package cl.daplay.jsurbtc.jackson.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ExceptionDTO {

    public static class ErrorDTO {

        @JsonProperty("resource")
        public String resource;

        @JsonProperty("field")
        public String field;

        @JsonProperty("code")
        public String code;

        @JsonProperty("message")
        public String message;

    }

    @JsonProperty("message")
    public String message;
    @JsonProperty("code")
    public String code;
    @JsonProperty("errors")
    public ErrorDTO[] errors;

    public ExceptionDTO() {
    }

}
