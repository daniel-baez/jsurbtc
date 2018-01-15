package cl.daplay.jsurbtc.jackson;

import cl.daplay.jsurbtc.Constants;
import cl.daplay.jsurbtc.jackson.dto.*;
import cl.daplay.jsurbtc.json.JSON;
import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.JSurbtcException;
import cl.daplay.jsurbtc.model.Page;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.balance.Balance;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.order.Order;
import cl.daplay.jsurbtc.model.order.OrderBook;
import cl.daplay.jsurbtc.model.trades.Trades;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonMap;

public enum JacksonJSON implements JSON {
    INSTANCE;

    public static ObjectMapper newObjectMapper() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return new ObjectMapper()
                .setDateFormat(simpleDateFormat)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    private final ObjectMapper objectMapper = newObjectMapper();
    private final DecimalFormat decimalFormat = Constants.newBigDecimalFormat();

    @Override
    public String newAPIKey(String name, Instant expiration) throws IOException {
        final Map payload = new LinkedHashMap<>();

        payload.put("name", name);
        payload.put("expiration_time", expiration);

        return objectMapper.writeValueAsString(singletonMap("api_key", payload));
    }

    @Override
    public String newOrder(String marketId, String orderType, String orderPriceType, BigDecimal qty, BigDecimal price) throws IOException {
        final Map payload = new LinkedHashMap<>();

        payload.put("type", orderType);
        payload.put("price_type", orderPriceType);
        payload.put("limit", decimalFormat.format(price));
        payload.put("amount", decimalFormat.format(qty));

        return objectMapper.writeValueAsString(singletonMap("order", payload));
    }

    @Override
    public String cancelOrder(long __) throws IOException {
        return objectMapper.writeValueAsString(singletonMap("state", "CANCELING"));
    }

    @Override
    public ApiKey apiKey(String json) throws IOException {
        return objectMapper.readValue(json, ApiKeyDTO.class).getApiKey();
    }

    @Override
    public List<Market> markets(String json) throws IOException {
        return objectMapper.readValue(json, MarketsDTO.class).getMarkets();
    }

    @Override
    public Order order(String json) throws IOException {
        return objectMapper.readValue(json, OrderDTO.class).getOrder();
    }

    @Override
    public Ticker ticker(String json) throws IOException {
        return objectMapper.readValue(json, TickerDTO.class).getTicker();
    }

    @Override
    public OrderBook orderBook(String json) throws IOException {
        return objectMapper.readValue(json, OrderBookDTO.class).getOrderBook();
    }

    @Override
    public Balance balance(String json) throws IOException {
        return objectMapper.readValue(json, BalanceDTO.class).getBalance();
    }

    @Override
    public Trades trades(String json) throws IOException {
        return objectMapper.readValue(json, TradesDTO.class).getTrades();
    }

    @Override
    public List<Balance> balances(String json) throws IOException {
        return objectMapper.readValue(json, BalancesDTO.class).getBalances();
    }

    @Override
    public List<Order> orders(String json) throws IOException {
        return objectMapper.readValue(json, OrdersDTO.class).getOrders();
    }

    @Override
    public List<Deposit> deposits(String json) throws IOException {
        return objectMapper.readValue(json, DepositsDTO.class).getDeposits();
    }

    @Override
    public List<Withdrawal> withdrawls(String json) throws IOException {
        return objectMapper.readValue(json, WithdrawalsDTO.class).getWithdrawals();
    }

    @Override
    public Page page(String json) throws IOException {
        return objectMapper.readValue(json, PageDTO.class).getMeta();
    }

    @Override
    public JSurbtcException exception(int statusCode, String json) throws Exception {
        final ExceptionDTO exceptionDTO = objectMapper.readValue(json, ExceptionDTO.class);

        if (null == exceptionDTO) {
            throw new Exception(format("Surbtc request failed. status code: '%d' response body: '%s'", statusCode, json));
        }

        return error2Error(statusCode, exceptionDTO);
    }

    private JSurbtcException.Detail error2Error(ExceptionDTO.ErrorDTO in) {
        return new JSurbtcException.Detail(in.resource, in.field, in.code, in.message);
    }

    private JSurbtcException error2Error(final int statusCode, ExceptionDTO in) {
        final ExceptionDTO.ErrorDTO[] dtos = in.errors == null ? new ExceptionDTO.ErrorDTO[0] : in.errors;

        final JSurbtcException.Detail[] details = stream(dtos).map(this::error2Error).toArray(JSurbtcException.Detail[]::new);
        return new JSurbtcException(statusCode, in.message, in.code, details);
    }

}
