package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.jackson.model.JacksonApiKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ApiKeyDTO {

    @JsonProperty("api_key")
    private final JacksonApiKey apiKey;

    @JsonCreator
    public ApiKeyDTO(@JsonProperty("api_key") JacksonApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public JacksonApiKey getApiKey() {
        return apiKey;
    }

    @Override
    public String toString() {
        return "APIKeyDTO{" +
                "apiKey=" + apiKey +
                '}';
    }

}
