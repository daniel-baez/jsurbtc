package cl.daplay.jsurbtc.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class ExceptionDTO {

    @JsonProperty("message")
    private final String message;

    @JsonCreator
    public ExceptionDTO(@JsonProperty("message") String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
