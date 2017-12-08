package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.jackson.dto.*;
import cl.daplay.jsurbtc.model.Amount;
import cl.daplay.jsurbtc.model.market.Market;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public final class Serialization_UT {

    private static final JSON OBJECT_MAPPER = JSON.INSTANCE;

    @Test
    public void exception() throws Exception {
        backAndForth(ExceptionDTO.class,
                "exception.json",
                "exception2.json");
    }

    @Test
    public void trades() throws Exception {
        backAndForth(TradesDTO.class,
                "trades1.json",
                "trades2.json",
                "trades3.json",
                "trades4.json",
                "trades5.json",
                "trades6.json",
                "trades7.json");
    }

    @Test
    public void balance_events() throws Exception {
        backAndForth(BalanceEventsDTO.class,
                "balance_events_btc.json",
                "balance_events_clp.json",
                "balance_events_eth.json");
    }

    @Test
    public void withdrawals() throws Exception {
        backAndForth(WithdrawalsDTO.class,
                "withdrawals_empty.json",
                "withdrawals_btc.json",
                "withdrawals_clp.json"
        );
    }

    @Test
    public void apikey() throws Exception {
        backAndForth(ApiKeyDTO.class, "api_key.json");
    }

    @Test
    public void deposits() throws Exception {
        backAndForth(DepositsDTO.class,
                "deposits_clp.json",
                "deposits_empty.json",
                "deposits_btc.json"
        );
    }

    @Test
    public void amount() throws Exception {
        backAndForth(Amount.class, "amount.json");
    }

    @Test
    public void market() throws Exception {
        backAndForth(Market.class, "market.json");
    }

    @Test
    public void markets() throws Exception {
        backAndForth(MarketsDTO.class, "markets.json");
    }

    @Test
    public void ticker() throws Exception {
        backAndForth(TickerDTO.class, "ticker.json");
    }

    @Test
    public void order_book() throws Exception {
        backAndForth(OrderBookDTO.class, "order_book.json");
    }

    @Test
    public void balances() throws Exception {
        backAndForth(BalancesDTO.class, "balances.json");
    }

    @Test
    public void balance() throws Exception {
        backAndForth(BalanceDTO.class, "balance.json");
    }

    @Test
    public void orders() throws Exception {
        backAndForth(OrdersDTO.class,
                "orders.json",
                "orders_empty.json",
                "orders2.json");
    }

    @Test
    public void order() throws Exception {
        backAndForth(OrderDTO.class,
                "order.json");
    }

    private <T> void backAndForth(final Class<T> valueType, final String... files) throws Exception {
        backAndForth(valueType, (value, file) -> {}, files);
    }

    private <T> Map<String, T> backAndForth(final Class<T> valueType, BiConsumer<T, String> forEach, final String... files) throws Exception {
        final Map<String, T> result = new LinkedHashMap<>();

        for (final String f : files) {
            final InputStream resourceAsStream = Serialization_UT.class.getResourceAsStream(f);
            final String input = Utils.convertStreamToString(resourceAsStream);
            try {
                final T parsed = OBJECT_MAPPER.parse(input, valueType);
                forEach.accept(parsed, f);
                final String written = OBJECT_MAPPER.stringify(parsed);

                assertEqualJSON(valueType, input);
                result.put(f, parsed);
            } catch (Throwable ex) {
                String t = "while parsing file: '%s' with valueType: '%s'";
                String m = format(t, f, valueType);
                throw new Exception(m, ex);
            }
        }

        return result;
    }

    private <T> void assertEqualJSON(Class<T> clazz, String input) throws IOException {
        final T fromInput = OBJECT_MAPPER.parse(input, clazz);
        final String output = OBJECT_MAPPER.stringify(fromInput);
        final T fromOutput = OBJECT_MAPPER.parse(output, clazz);

        final boolean txtEquals = minify(input).equals(minify(output));
        if (!txtEquals) {
            assertEquals(fromInput, fromOutput);
        }
    }

    private String minify(String json) {
        return json.replaceAll("\\s", "");
    }


}
