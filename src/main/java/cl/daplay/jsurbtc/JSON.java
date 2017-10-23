package cl.daplay.jsurbtc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;

public enum JSON {
    INSTANCE;

    private final static Logger LOGGER = Logger.getLogger(JSON.class.getName());

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
            return stringify(value).orElse("");
        };
    }

    Optional<String> stringify(final Object value) {
        try {
            final String s = objectMapper.writeValueAsString(value);
            return Optional.of(s);
        } catch (final JsonProcessingException ex) {
            LOGGER.log(Level.WARNING, ex, () -> format("While deserializing value: '%s' with type: '%s' to JSON.", value, value == null ? "null" : value.getClass().getName()));
            return Optional.empty();
        }
    }

    <T> Optional<T> parse(final String json, final Class<T> valueType) {
        try {
            final T t = objectMapper.readValue(json, valueType);
            return Optional.of(t);
        } catch (final IOException ex) {
            LOGGER.log(Level.WARNING, ex, () -> format("While deserializing value with type: '%s' to JSON.", valueType));
            return Optional.empty();
        }
    }

}
