package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.model.Market;
import cl.daplay.jsurbtc.model.OrderBook;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.Trades;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JSurbtc_IT {

    JSurbtc surbtc;

    @Before
    public void before() {
        surbtc = new JSurbtc();
    }

    @Test
    public void test_get_markets() throws Exception {
        List<Market> markets = surbtc.getMarkets();
        Assert.assertFalse(markets.isEmpty());

        for (Market market : markets) {
            System.out.println(market);
        }
    }

    @Test
    public void test_get_tickers() throws Exception {
        List<Market> markets = surbtc.getMarkets();

        for (Market market : markets) {
            Ticker ticker = surbtc.getTicker(market.getId());
            System.out.println(market);
            System.out.println(ticker);
        }
    }

    @Test
    public void test_get_order_book() throws Exception {
        List<Market> markets = surbtc.getMarkets();

        for (Market market : markets) {
            OrderBook orderBook = surbtc.getOrderBook(market.getId());
            System.out.println(market);
            System.out.println(orderBook);
        }
    }

    @Test
    public void test_get_trades() throws Exception {
        List<Market> markets = surbtc.getMarkets();

        for (Market market : markets) {
            Trades trades = surbtc.getTrades(market.getId());
            System.out.println(market);
            System.out.println(trades);
        }
    }


}
