package cl.daplay.jsurbtc.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import static cl.daplay.jsurbtc.Constants.newBigDecimalFormat;

public final class BigDecimalToStringSerializer extends JsonSerializer<BigDecimal> {

    private static final DecimalFormat BIG_DECIMAL_FORMAT = newBigDecimalFormat();

    @Override
    public void serialize(BigDecimal amount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(BIG_DECIMAL_FORMAT.format(amount));
    }
}
