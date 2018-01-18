package cl.daplay.jsurbtc.jackson;

import cl.daplay.jsurbtc.JSON;
import cl.daplay.jsurbtc.model.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static cl.daplay.jsurbtc.Utils.convertStreamToString;
import static java.lang.String.format;

public class JacksonJSON_UT {

    private final JSON json = JacksonJSON.INSTANCE;

    @Test
    public void newAPIKey() throws IOException {
        Instant expiration = Instant.now();
        String name = UUID.randomUUID().toString();

        String out = json.newAPIKey(name, expiration);
        ApiKey apiKey = json.apiKey(out);

        Assert.assertEquals(expiration, apiKey.getExpirationTime());
        Assert.assertEquals(name, apiKey.getName());

        System.out.println(out);
        System.out.println(apiKey);
    }

    @Test
    public void newOrder() throws IOException {
        Random random = new Random();

        String marketId = UUID.randomUUID().toString();
        String orderType = UUID.randomUUID().toString();
        String orderPriceType = UUID.randomUUID().toString();

        BigDecimal qty = new BigDecimal(random.nextInt());
        BigDecimal price = new BigDecimal(random.nextInt());

        String out = json.newOrder(marketId, orderType, orderPriceType, qty, price);

        System.out.println(out);
    }

    @Test
    public void cancelOrder() throws IOException {
        Random random = new Random();

        String out = json.cancelOrder(random.nextLong());
        Assert.assertTrue("{\"state\":\"CANCELING\"}".equals(out));
    }

    @Test
    public void apiKey() throws IOException {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/api_key.json"));
        ApiKey apiKey = json.apiKey(jsonExample);

        Assert.assertTrue(jsonExample.contains(apiKey.getId()));
        Assert.assertTrue(jsonExample.contains(apiKey.getName()));
        Assert.assertTrue(jsonExample.contains(apiKey.getSecret()));
    }

    @Test
    public void markets() throws IOException {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/markets.json"));
        List<Market> markets = json.markets(jsonExample);

        Assert.assertFalse(markets.isEmpty());
    }

    @Test
    public void order() throws IOException {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/order.json"));
        Order order = json.order(jsonExample);

        Assert.assertTrue(jsonExample.contains(order.getId() + ""));
        Assert.assertTrue(jsonExample.contains(order.getType()));
        Assert.assertTrue(jsonExample.contains(order.getState()));
    }

    @Test
    public void ticker() throws IOException {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/ticker.json"));
        Ticker ticker = json.ticker(jsonExample);

        Assert.assertTrue(ticker.getLastPrice().longValueExact() == 1749000);
    }

    @Test
    public void trades() throws IOException {
        for (int i = 1; i < 8; i++) {
            String fileName = format("/trades%d.json", i);
            String jsonExample = convertStreamToString(getClass().getResourceAsStream(fileName));
            Trades trades = json.trades(jsonExample);

            List<Trades.Transaction> transactions = trades.getEntries();
            Trades.Transaction lastTransaction = transactions.get(transactions.size() - 1);

            Assert.assertTrue(trades.getLastTimestamp().equals(lastTransaction.getTimestamp()));
        }
    }

    @Test
    public void order_book() throws IOException {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/order_book.json"));
        OrderBook orderBook = json.orderBook(jsonExample);

        Assert.assertTrue(orderBook.getAsks().size() == 35);
        Assert.assertTrue(orderBook.getBids().size() == 72);
    }

    @Test
    public void balances() throws IOException {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/balances.json"));
        List<Balance> balances = json.balances(jsonExample);

        Assert.assertTrue(balances.size() == 4);
    }

    @Test
    public void balance() throws IOException {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/balance.json"));
        Balance balance = json.balance(jsonExample);

        Assert.assertTrue(balance.getAccountId() == 1898);
    }

    @Test
    public void orders() throws IOException {
        List<String> filenames = Arrays.asList("/orders.json", "/orders2.json", "/orders_empty.json");

        for (String filename: filenames) {
            String jsonExample = convertStreamToString(getClass().getResourceAsStream(filename));
            List<Order> orders = json.orders(jsonExample);

            if (filename.endsWith("_empty.json")) {
                Assert.assertTrue(orders.isEmpty());
            } else {
                Assert.assertFalse(orders.isEmpty());
            }
        }
    }

    @Test
    public void deposits() throws IOException {
        List<String> filenames = Arrays.asList("/deposits_btc.json", "/deposits_clp.json", "/deposits_empty.json");

        for (String filename: filenames) {
            String jsonExample = convertStreamToString(getClass().getResourceAsStream(filename));
            List<Deposit> deposits = json.deposits(jsonExample);

            if (filename.endsWith("_empty.json")) {
                Assert.assertTrue(deposits.isEmpty());
            } else {
                Assert.assertFalse(deposits.isEmpty());
            }
        }
    }

    @Test
    public void withdrawls() throws IOException {
        List<String> filenames = Arrays.asList("/withdrawals_btc.json", "/withdrawals_clp.json", "/withdrawals_empty.json");

        for (String filename: filenames) {
            String jsonExample = convertStreamToString(getClass().getResourceAsStream(filename));
            List<Withdrawal> withdrawls = json.withdrawls(jsonExample);

            if (filename.endsWith("_empty.json")) {
                Assert.assertTrue(withdrawls.isEmpty());
            } else {
                Assert.assertFalse(withdrawls.isEmpty());
            }
        }

    }

    @Test
    public void page() throws IOException {
        // List<String> filenames = Arrays.asList(
        //         "/withdrawals_btc.json",
        //         "/withdrawals_clp.json",
        //         "/withdrawals_empty.json",
        //         "/orders2.json",
        //         "/orders.json",
        //         "/orders_empty.json",
        //         "/deposits_btc.json",
        //         "/deposits_clp.json",
        //         "/deposits_empty.json");

        List<String> filenames = Arrays.asList("/withdrawals_btc.json");

        for (String filename: filenames) {
            String jsonExample = convertStreamToString(getClass().getResourceAsStream(filename));
            Page page = json.page(jsonExample);

        }

        /// aqui hay que probar todos los archivos de ejemplo y punto
    }

    @Test
    public void exception() throws Exception {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/exception.json"));
        JSurbtcException exception = json.exception(400, jsonExample);
        Assert.assertTrue(exception.message.equals("authentication failed"));
    }

    @Test
    public void exception2() throws Exception {
        String jsonExample = convertStreamToString(getClass().getResourceAsStream("/exception2.json"));
        JSurbtcException exception = json.exception(400, jsonExample);
        Assert.assertTrue(exception.message.equals("Validation Failed"));
        Assert.assertTrue(exception.code.equals("invalid_record"));
        Assert.assertTrue(exception.details.length == 1);
        Assert.assertTrue(exception.details[0].resource.equals("Bid"));
        Assert.assertTrue(exception.details[0].field.equals("amount_cents"));
        Assert.assertTrue(exception.details[0].code.equals("insolvent"));
        Assert.assertTrue(exception.details[0].message.equals("insolvent"));
    }

}
