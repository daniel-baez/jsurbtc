package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.Currency;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.balance.Balance;
import cl.daplay.jsurbtc.model.balance.BalanceEvent;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.market.MarketID;
import cl.daplay.jsurbtc.model.order.*;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import org.apache.http.HttpHost;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.junit.Assert.*;

public class JSurbrc_IT {

    // TODO: a better way to fill this
    String key;
    String secret;

    private JSurbtc newClient() {
        return new JSurbtc(key, secret, JSurbtc.newNonce(), new HttpHost("localhost", 8888));
    }

    @Test
    public void get_deposits() {
        final JSurbtc client = newClient();

        for (Currency currency : Currency.values()) {
            System.out.printf("Deposits for Currency: %s%n", currency);
            final List<Deposit> deposits = client.getDeposits(currency);

            for (Deposit deposit : deposits) {
                System.out.printf("\t%s%n", deposit);
            }
        }
    }

    @Test
    public void get_withdrawals() {
        final JSurbtc client = newClient();

        for (Currency currency : Currency.values()) {
            System.out.printf("Withdrawals for Currency: %s%n", currency);
            final List<Withdrawal> withdrawals = client.getWithdrawals(currency);

            for (Withdrawal withdrawal : withdrawals) {
                System.out.printf("\t%s%n", withdrawal);
            }
        }
    }

    @Test
    public void get_balance_events() {
        final JSurbtc client = newClient();

        for (Currency currency : Currency.values()) {
            System.out.printf("Balances for Currency: %s%n", currency);
            final List<BalanceEvent> balanceEvents = client.getBalanceEvents(currency);

            for (BalanceEvent balanceEvent : balanceEvents) {
                System.out.printf("\t%s%n", balanceEvent);
            }
        }
    }

    @Test
    public void new_api_key() {
        final JSurbtc client = newClient();

        final Instant expiration = Instant.now().plus(10, ChronoUnit.SECONDS);
        final String name = format("test-newAPIKey-%s", UUID.randomUUID().toString());

        final Optional<ApiKey> apiKey = client.newAPIKey(name, expiration);
        assertTrue("apiKey should work", apiKey.isPresent());

        System.out.printf("%s%n", apiKey.get());
    }

    @Test
    public void new_order__and__cancel_order() {
        final JSurbtc client = newClient();

        final Order order = client.newOrder(MarketID.BTC_CLP,
                OrderType.BID,
                OrderPriceType.LIMIT,
                BigDecimal.ONE,
                BigDecimal.ONE).orElse(null);

        assertNotNull("newOrder should work", order);
        System.out.printf("order= %s%n", order);

        final Order cancelOrder = client.cancelOrder(order.getId()).orElse(null);
        assertNotNull("cancelOrder should work", cancelOrder);
        System.out.printf("cancelledOrder=%s%n", order);
    }

    @Test
    public void get_markets() {
        final JSurbtc client = newClient();

        final Set<MarketID> expected = EnumSet.allOf(MarketID.class);

        final Set<MarketID> actual = client.getMarkets().stream()
                .map(Market::getId)
                .collect(Collectors.toSet());

        final String m = format("expected: %s, actual:%s%n", expected, actual);
        assertEquals(m, actual, expected);
    }

    @Test
    public void get_order_book() {
        final JSurbtc client = newClient();

        final List<Market> markets = client.getMarkets();
        assertTrue("getMarkets works", !markets.isEmpty());

        for (final Market market : markets) {
            final Optional<OrderBook> maybeOrderBook = client.getOrderBook(market.getId());
            assertTrue("getOrderBook works", maybeOrderBook.isPresent());

            final OrderBook orderBook = maybeOrderBook.get();
            System.out.printf("%s%n", orderBook);
        }
    }

    @Test
    public void get_balance() {
        final JSurbtc client = newClient();

        final List<Balance> balances = client.getBalances();

        System.out.printf("balances: %s:%n", balances);

        for (final Balance expected : balances) {
            final Balance actual = client.getBalance(expected.getId()).orElse(null);

            final String t = "expected: %s, actual: %s:%n";
            final String m = format(t, expected, actual);

            assertEquals(m, expected, actual);
        }
    }

    @Test
    public void get_ticker() {
        final JSurbtc client = newClient();
        final List<Market> markets = client.getMarkets();

        for (final Market market : markets) {
            final MarketID marketId = market.getId();
            final Optional<Ticker> maybeTicker = client.getTicker(marketId);

            assertTrue("client.getTicket() should be successful.", maybeTicker.isPresent());

            final Ticker ticker = maybeTicker.get();
            System.out.printf("ticker:%s%n", ticker);
        }
    }

    @Test
    public void get_orders() {
        final JSurbtc client = newClient();
        final List<Market> markets = client.getMarkets();

        final Map<String, Supplier<List<Order>>> tests = new LinkedHashMap<>();

        // orders for every market
        for (final Market market : markets) {
            final MarketID marketId = market.getId();

            tests.put(format("Orders for Market:%s%n", marketId), () -> {
                return client.getOrders(marketId);
            });
        }

         for (final Market market : markets) {
             final MarketID marketId = market.getId();

             for (OrderState orderState : OrderState.values()) {
                 tests.put(format("Orders for Market:%s in State: %s %n", marketId, orderState), () -> {
                     return client.getOrders(marketId, orderState);
                 });
             }
         }

        for (final Market market : markets) {
            final MarketID marketId = market.getId();

            tests.put(format("Orders for Market:%s in minimal exchange: %s %n", marketId, BigDecimal.ONE), () -> {
                return client.getOrders(marketId, BigDecimal.ONE);
            });
        }

        for (final Market market : markets) {
            final MarketID marketId = market.getId();

            for (OrderState orderState : OrderState.values()) {
                tests.put(format("Orders for Market:%s in State: %s minimal exchange: %s %n", marketId, orderState, BigDecimal.ONE), () -> {
                    return client.getOrders(marketId, orderState, BigDecimal.ONE);
                });
            }
        }

        tests.forEach((name, ordersSupplier) -> {
            System.out.println(name);
            final List<Order> orders = ordersSupplier.get();

            for (final Order order : orders) {
                final Order order1 = client.getOrder(order.getId()).orElse(null);

                assertEquals(order, order1);

                System.out.printf("\t %s%n", order);
            }
        });
    }

}
