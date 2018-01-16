package cl.daplay.jsurbtc.jackson;

import cl.daplay.jsurbtc.JSON;
import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.JSurbtcException;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.order.Order;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static cl.daplay.jsurbtc.Utils.convertStreamToString;

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

        System.out.println(out);
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

        System.out.println(ticker);

        // Assert.assertTrue(jsonExample.contains(ticker.getId() + ""));
        // Assert.assertTrue(jsonExample.contains(ticker.get()));
        // Assert.assertTrue(jsonExample.contains(ticker.getState()));
    }

    @Test
    public void orderBook() {
    }

    @Test
    public void balance() {
    }

    @Test
    public void trades() {
    }

    @Test
    public void balances() {
    }

    @Test
    public void orders() {
    }

    @Test
    public void deposits() {
    }

    @Test
    public void withdrawls() {
    }

    @Test
    public void page() {
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
