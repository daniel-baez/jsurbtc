package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.dto.*;
import cl.daplay.jsurbtc.model.Amount;
import cl.daplay.jsurbtc.model.market.Market;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

public final class Serialization_UT {

    private static final ObjectMapper OBJECT_MAPPER = JSON.newObjectMapper();

    @Test
    public void exception() throws Exception {
        backAndForth(ExceptionDTO.class,
                "exception.json",
                "exception2.json");
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
                (dto, file) -> {
                    System.out.printf("%s:%n", file);
                    dto.getDeposits().forEach(it -> System.out.printf("- %s%n", it));

                },
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
        backAndForth(BalanceDTO.class, "balance.json");
    }

    @Test
    public void orders() throws Exception {
        backAndForth(OrdersDTO.class,
                "orders.json",
                "orders_empty.json",
                "orders2.json");

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
            final String input = IOUtils.toString(resourceAsStream, Charset.defaultCharset());
            try {
                final T parsed = OBJECT_MAPPER.readValue(input, valueType);
                forEach.accept(parsed, f);
                final String written = OBJECT_MAPPER.writeValueAsString(parsed);

                if (false) {
                    System.out.printf("valueType: %s%n", valueType);
                    System.out.printf("input: %s%n", input);
                    System.out.printf("parsed: %s%n", parsed);
                    System.out.printf("written: %s%n", written);
                }

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

    private void assertEqualJSON(String input, String written) {
        assertEquals(minify(input), minify(written));
    }

    private <T> void assertEqualJSON(Class<T> clazz, String input) throws IOException {
        final T fromInput = OBJECT_MAPPER.readValue(input, clazz);
        final String output = OBJECT_MAPPER.writeValueAsString(fromInput);
        final T fromOutput = OBJECT_MAPPER.readValue(output, clazz);

        final boolean txtEquals = minify(input).equals(minify(output));
        if (!txtEquals) {
            assertEquals(fromInput, fromOutput);
        }
    }

    private String minify(String json) {
        return json.replaceAll("\\s", "");
    }


}
