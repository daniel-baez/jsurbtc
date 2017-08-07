package cl.daplay.jsurbtc.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public final class BigDecimalToStringSerializer extends JsonSerializer<BigDecimal> {

    private static final DecimalFormat FORMAT = newFormat();

    public static DecimalFormat newFormat(){
        final DecimalFormat format = new DecimalFormat();

        final DecimalFormatSymbols decimalFormatSymbols = format.getDecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');

        format.setMaximumFractionDigits(9);
        format.setMinimumFractionDigits(1);
        format.setGroupingUsed(false);
        format.setDecimalFormatSymbols(decimalFormatSymbols);

        return format;
    }

    @Override
    public void serialize(BigDecimal amount, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(FORMAT.format(amount));
    }
}
