package cl.daplay.jsurbtc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.function.Supplier;
import java.util.logging.Logger;

public enum JSON {
    INSTANCE;

    public static ObjectMapper newObjectMapper() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return new ObjectMapper()
                .setDateFormat(simpleDateFormat)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    private final ObjectMapper objectMapper = newObjectMapper();

    Supplier<String> payload(final Object value) {
        return () -> {
            try {
                return stringify(value);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }

    String stringify(final Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }

    <T> T parse(final String json, final Class<T> valueType) throws IOException {
        return objectMapper.readValue(json, valueType);
    }

}
