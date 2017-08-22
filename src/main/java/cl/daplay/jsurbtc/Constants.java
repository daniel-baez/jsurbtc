package cl.daplay.jsurbtc;

import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public interface Constants {

    static DecimalFormat newBigDecimalFormat(){
        final DecimalFormat format = new DecimalFormat();

        final DecimalFormatSymbols decimalFormatSymbols = format.getDecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');

        format.setMaximumFractionDigits(9);
        format.setMinimumFractionDigits(1);
        format.setGroupingUsed(false);
        format.setDecimalFormatSymbols(decimalFormatSymbols);

        return format;
    }

    MathContext DEFAULT_MATH_CONTEXT = new MathContext(9, RoundingMode.HALF_UP);

}
