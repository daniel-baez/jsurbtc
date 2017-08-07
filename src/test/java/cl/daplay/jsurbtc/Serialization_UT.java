package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.dto.*;
import cl.daplay.jsurbtc.model.Amount;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

public final class Serialization_UT {

    private static final ObjectMapper OBJECT_MAPPER = JSurbtc.buildObjectMapper();

    private void assertEqualJSON(String input, String written) {
        assertEquals(minify(input), minify(written));
    }

    private String minify(String json) {
        return json.replaceAll("\\s", "");
    }

    @Test
    public void exception() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("exception.json"), Charset.defaultCharset());
        final ExceptionDTO parsed = OBJECT_MAPPER.readValue(input, ExceptionDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void withdrawals_empty() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("withdrawals_empty.json"), Charset.defaultCharset());
        final WithdrawalsDTO parsed = OBJECT_MAPPER.readValue(input, WithdrawalsDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void withdrawals_btc() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("withdrawals_btc.json"), Charset.defaultCharset());
        final WithdrawalsDTO parsed = OBJECT_MAPPER.readValue(input, WithdrawalsDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void withdrawal_btc() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("withdrawal_btc.json"), Charset.defaultCharset());
        final Withdrawal parsed = OBJECT_MAPPER.readValue(input, Withdrawal.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void withdrawals_clp() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("withdrawals_clp.json"), Charset.defaultCharset());
        final WithdrawalsDTO parsed = OBJECT_MAPPER.readValue(input, WithdrawalsDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void withdrawal_clp() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("withdrawal_clp.json"), Charset.defaultCharset());
        final Withdrawal parsed = OBJECT_MAPPER.readValue(input, Withdrawal.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void deposits_empty() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("deposits_empty.json"), Charset.defaultCharset());
        final DepositsDTO parsed = OBJECT_MAPPER.readValue(input, DepositsDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void deposits_btc() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("deposits_btc.json"), Charset.defaultCharset());
        final DepositsDTO parsed = OBJECT_MAPPER.readValue(input, DepositsDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void deposits_clp() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("deposits_clp.json"), Charset.defaultCharset());
        final DepositsDTO parsed = OBJECT_MAPPER.readValue(input, DepositsDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void deposit_btc() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("deposit_btc.json"), Charset.defaultCharset());
        final Deposit parsed = OBJECT_MAPPER.readValue(input, Deposit.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void deposit_clp() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("deposit_clp.json"), Charset.defaultCharset());
        final Deposit parsed = OBJECT_MAPPER.readValue(input, Deposit.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void amount() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("amount.json"), Charset.defaultCharset());
        final Amount parsed = OBJECT_MAPPER.readValue(input, Amount.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void market() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("market.json"), Charset.defaultCharset());
        final Market parsed = OBJECT_MAPPER.readValue(input, Market.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void markets() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("markets.json"), Charset.defaultCharset());
        final MarketsDTO parsed = OBJECT_MAPPER.readValue(input, MarketsDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void ticker() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("ticker.json"), Charset.defaultCharset());
        final TickerDTO parsed = OBJECT_MAPPER.readValue(input, TickerDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void order_book() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("order_book.json"), Charset.defaultCharset());
        final OrderBookDTO parsed = OBJECT_MAPPER.readValue(input, OrderBookDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void balance() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("balance.json"), Charset.defaultCharset());
        final BalanceDTO parsed = OBJECT_MAPPER.readValue(input, BalanceDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void balances() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("balances.json"), Charset.defaultCharset());
        final BalancesDTO parsed = OBJECT_MAPPER.readValue(input, BalancesDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void orders() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("orders.json"), Charset.defaultCharset());
        final OrdersDTO parsed = OBJECT_MAPPER.readValue(input, OrdersDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void orders_empty() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("orders_empty.json"), Charset.defaultCharset());
        final OrdersDTO parsed = OBJECT_MAPPER.readValue(input, OrdersDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

    @Test
    public void order() throws IOException {
        final String input = IOUtils.toString(Serialization_UT.class.getResourceAsStream("order.json"), Charset.defaultCharset());
        final OrderDTO parsed = OBJECT_MAPPER.readValue(input, OrderDTO.class);
        final String written = OBJECT_MAPPER.writeValueAsString(parsed);

        assertEqualJSON(input, written);
    }

}
