package cl.daplay.jsurbtc.jackson.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

public class APIKeyRequestDTO {

    @JsonProperty("api_key")
    private final Map<String, Object> content;

    public APIKeyRequestDTO(String name, Instant expirationTime) {
        this.content = new LinkedHashMap<>();
        this.content.put("name", name);
        this.content.put("expiration_time", expirationTime);
    }

}
