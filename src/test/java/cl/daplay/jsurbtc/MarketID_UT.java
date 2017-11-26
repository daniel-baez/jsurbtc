package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.model.Currency;
import cl.daplay.jsurbtc.model.market.MarketID;
import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class MarketID_UT {

    /**
     * checks BaseCurrency and QuoteCurrency for MarketID instances
     */
    @Test
    public void test() {
        final Pattern pattern = Pattern.compile("([A-Z]{3})[\\-_]([A-Z]{3})");

        for (final MarketID marketID : MarketID.values()) {
            final String marketIdName = marketID.name();
            final Matcher matcher = pattern.matcher(marketIdName);

            final String firstAssertion = format("MarketID: %s should match pattern %s", marketID, pattern);
            Assert.assertTrue(firstAssertion, matcher.matches()) ;

            final String expectedBaseCurrency = matcher.group(1);
            final String expectedQuoteCurrency = matcher.group(2);

            final Currency baseCurrency = marketID.getBaseCurrency();
            final Currency quoteCurrency = marketID.getQuoteCurrency();

            final String secondAssertion = format("MarketID: %s baseCurrency should be: %s but is: %s", marketID, expectedBaseCurrency, baseCurrency);
            Assert.assertEquals(secondAssertion, expectedBaseCurrency, baseCurrency.name());

            final String thirdAssertion = format("MarketID: %s quoteCurrency should be: %s but is: %s", marketID, expectedQuoteCurrency, quoteCurrency);
            Assert.assertEquals(thirdAssertion, expectedQuoteCurrency, quoteCurrency.name());
        }
    }

}
