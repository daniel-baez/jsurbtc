package cl.daplay.jsurbtc.jackson;

import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static cl.daplay.jsurbtc.Constants.newBigDecimalFormat;
import static org.junit.Assert.assertEquals;

public final class BigDecimalFormat_UT {

    @Test
    public void test() {
        DecimalFormat decimalFormat = newBigDecimalFormat();

        assertEquals("0.4", decimalFormat.format(new BigDecimal("0.4")));
        assertEquals("0.4", decimalFormat.format(new BigDecimal(0.4)));
        assertEquals("0.001", decimalFormat.format(new BigDecimal(1).divide(new BigDecimal(1000))));
        assertEquals("1000000.0", decimalFormat.format(new BigDecimal(1000000)));
    }

}
