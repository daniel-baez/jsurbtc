package cl.daplay.jsurbtc.jackson.dto;

import cl.daplay.jsurbtc.model.ApiKey;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public final class ApiKeyDTO {

    @JsonProperty("api_key")
    private final ApiKey apiKey;

    @JsonCreator
    public ApiKeyDTO(@JsonProperty("api_key") ApiKey apiKey) {
        this.apiKey = apiKey;
    }

    public ApiKey getApiKey() {
        return apiKey;
    }

    @Override
    public String toString() {
        return "APIKeyDTO{" +
                "apiKey=" + apiKey +
                '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ApiKeyDTO apiKeyDTO = (ApiKeyDTO) o;

        return apiKey != null ? apiKey.equals(apiKeyDTO.apiKey) : apiKeyDTO.apiKey == null;
    }

    @Override
    public int hashCode() {
        return apiKey != null ? apiKey.hashCode() : 0;
    }

}
