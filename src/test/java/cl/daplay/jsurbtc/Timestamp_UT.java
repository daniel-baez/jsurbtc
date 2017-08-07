package cl.daplay.jsurbtc;


import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class Timestamp_UT {

    @Test
    public void test() throws ParseException {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        String[] inputs = new String[]{"2015-03-26T20:05:52.001Z", "2015-03-26T20:05:52.000Z"};

        for (String input : inputs) {
            Date parsed = format.parse(input);
            String output = format.format(parsed);

            assertEquals("should be equal", input, output);
        }
    }

}
