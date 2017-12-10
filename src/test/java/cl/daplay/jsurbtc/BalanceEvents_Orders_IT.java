package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.Currency;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.balance.Balance;
import cl.daplay.jsurbtc.model.balance.BalanceEvent;
import cl.daplay.jsurbtc.model.balance.BalanceEventType;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.market.MarketID;
import cl.daplay.jsurbtc.model.order.*;
import cl.daplay.jsurbtc.model.trades.Trades;
import cl.daplay.jsurbtc.model.trades.Transaction;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.LongFunction;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class BalanceEvents_Orders_IT extends IT {

    @Test
    public void balance_per_order() throws Exception {
        final JSurbtc client = newClient();

        // for markets
        final EnumSet<MarketID> markets = EnumSet.of(MarketID.BTC_CLP, MarketID.ETH_CLP, MarketID.BCH_CLP);

        for (MarketID market : markets) {
            // all orders
            final List<Order> orders = reverse(client.getOrders(market, OrderState.TRADED));

            // create an event
            final List<BalanceEvent> events = client.getBalanceEvents(market.getBaseCurrency())
                    .stream()
                    .filter(balanceEvent -> balanceEvent.getType() == BalanceEventType.TRANSACTION)
                    .collect(collectingAndThen(toList(), this::reverse));

            assertEquals(format("each order should trigger a balance change."), orders.size(), events.size());

            for (int i = 0; i < events.size(); i++) {
                final Order order = orders.get(i);
                final BalanceEvent event = events.get(i);

                // TODO: dates should somewhat match

                // amounts should match
                final int compareTo = order.getActualAmount().compareTo(event.getDeltaAmount().abs());
                assertEquals("event's delta doesn't match order's amount", 0 , compareTo);
            }
        }
    }

    private <T> List<T> reverse(List<T> input) {
        final ArrayList<T> result = new ArrayList<>(input.size());

        for (int i = input.size() - 1; i >= 0; i--) {
            result.add(input.get(i));
        }

        return result;
    }

}
