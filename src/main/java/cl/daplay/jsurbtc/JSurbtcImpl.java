package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.jackson.dto.*;
import cl.daplay.jsurbtc.jackson.dto.request.APIKeyRequestDTO;
import cl.daplay.jsurbtc.jackson.dto.request.OrderRequestDTO;
import cl.daplay.jsurbtc.http.DefaultHTTPClient;
import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.Currency;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.balance.Balance;
import cl.daplay.jsurbtc.model.balance.BalanceEvent;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.market.MarketID;
import cl.daplay.jsurbtc.model.order.*;
import cl.daplay.jsurbtc.model.trades.Trades;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.logging.Logger;

import static cl.daplay.jsurbtc.Constants.newBigDecimalFormat;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonMap;

public class JSurbtcImpl implements JSurbtc {

    private final static Logger LOGGER = Logger.getLogger(JSurbtcImpl.class.getName());

    private final DecimalFormat bigDecimalFormat;
    private final HTTPClient httpClient;
    private final JSON json;

    public JSurbtcImpl(final String key, final String secret) {
        this(key, secret, JSurbtc.newNonce(), null);
    }

    public JSurbtcImpl(final String key, final String secret, final LongSupplier nonceSupplier) {
        this(key, secret, nonceSupplier, null);
    }

    public JSurbtcImpl(final String key, final String secret, final LongSupplier nonceSupplier, final InetSocketAddress httpProxy) {
        this(key, secret, nonceSupplier, JSON.INSTANCE, httpProxy == null ? null : new Proxy(Proxy.Type.HTTP, httpProxy));
    }

    public JSurbtcImpl(final String key, final String secret, final LongSupplier nonceSupplier, final JSON json, final Proxy proxy) {
        this(new DefaultHTTPClient(proxy, secret, key, nonceSupplier), newBigDecimalFormat(), json);
    }

    public JSurbtcImpl(JSurbtcImpl other) {
        this.bigDecimalFormat = other.bigDecimalFormat;
        this.httpClient = other.httpClient;
        this.json = other.json;
    }

    JSurbtcImpl(final HTTPClient httpClient, final DecimalFormat bigDecimalFormat, final JSON json) {
        this.httpClient = httpClient;
        this.bigDecimalFormat = bigDecimalFormat;
        this.json = json;
    }

    @Override
    public ApiKey newAPIKey(final String name, final Instant expiration) throws Exception {
        final String path = "/api/v2/api_keys";
        return post(path, new APIKeyRequestDTO(name, expiration), parser(ApiKeyDTO.class, ApiKeyDTO::getApiKey));
    }

    @Override
    public Order newOrder(final MarketID marketId, final OrderType orderType, final OrderPriceType orderPriceType, final BigDecimal qty, final BigDecimal price) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        final OrderRequestDTO payload = new OrderRequestDTO(bigDecimalFormat, orderType, orderPriceType, qty, price);
        return post(path, payload, parser(OrderDTO.class, OrderDTO::getOrder));
    }

    @Override
    public Trades getTrades(final MarketID marketId, final Instant timestamp) throws Exception {
        String path = format("/api/v2/markets/%s/trades", marketId).toLowerCase();

        if (timestamp != null) {
            path += "?timestamp=" + timestamp.toEpochMilli();
        }

        return get(path, parser(TradesDTO.class, TradesDTO::getTrades));
    }

    @Override
    public Trades getTrades(final MarketID marketId) throws Exception {
        return getTrades(marketId, null);
    }

    @Override
    public Order cancelOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId);
        return httpClient.put(path, json.payload(singletonMap("state", OrderState.CANCELING)), handlingErrors(parser(OrderDTO.class, OrderDTO::getOrder)));
    }

    @Override
    public List<Market> getMarkets() throws Exception {
        final String path = "/api/v2/markets";
        return get(path, MarketsDTO.class, MarketsDTO::getMarkets);
    }

    @Override
    public Ticker getTicker(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/ticker", marketId).toLowerCase();
        return get(path, TickerDTO.class, TickerDTO::getTicker);
    }

    @Override
    public OrderBook getOrderBook(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/order_book", marketId).toLowerCase();
        return get(path, OrderBookDTO.class, OrderBookDTO::getOrderBook);
    }

    @Override
    public Balance getBalance(final Currency currency) throws Exception {
        final String path = format("/api/v2/balances/%s", currency).toLowerCase();
        return get(path, BalanceDTO.class, BalanceDTO::getBalance);
    }

    @Override
    public List<Balance> getBalances() throws Exception {
        return get("/api/v2/balances", BalancesDTO.class, BalancesDTO::getBalances);
    }

    @Override
    public List<Order> getOrders(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    @Override
    public List<Order> getOrders(final MarketID marketId, final OrderState orderState) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&algo=", marketId, orderState).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    @Override
    public List<Order> getOrders(final MarketID marketId, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?minimun_exchanged=%s", marketId, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    @Override
    public List<Order> getOrders(final MarketID marketId, final OrderState orderState, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&minimun_exchanged=%s", marketId, orderState, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    @Override
    public Order getOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId).toLowerCase();
        return get(path, OrderDTO.class, OrderDTO::getOrder);
    }

    @Override
    public List<Deposit> getDeposits(final Currency currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/deposits", currency).toLowerCase();
        return newPaginatedList(path, DepositsDTO.class, DepositsDTO::getPagination, DepositsDTO::getDeposits);
    }

    @Override
    public List<Withdrawal> getWithdrawals(final Currency currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/withdrawals", currency).toLowerCase();
        return newPaginatedList(path, WithdrawalsDTO.class, WithdrawalsDTO::getPagination, WithdrawalsDTO::getWithdrawals);
    }

    @Override
    public List<BalanceEvent> getBalanceEvents(final Currency currency) throws Exception {
        final String path = "/api/v2/balance_events";
        final String params = "?currencies%5B%5D=" + currency + "&event_names%5B%5D=deposit_confirm&event_names%5B%5D=withdrawal_confirm&event_names%5B%5D=transaction&event_names%5B%5D=transfer_confirmation&relevant=true";

        return get(path + params, BalanceEventsDTO.class, BalanceEventsDTO::getBalanceEvents);
    }

    // ** implementation methods **

    private <T, K> JSurbtcPaginatedList<T> newPaginatedList(final String path,
                                                            final Class<K> dtoType,
                                                            final Function<K, PaginationDTO> getPagination,
                                                            final Function<K, List<T>> getPage) throws Exception {
        return get(path, (__, responseBody) -> {
            final K dto = json.parse(responseBody, dtoType);

            final PaginationDTO pagination = getPagination.apply(dto);
            final int totalPages = pagination.getTotalPages();
            final int totalCount = pagination.getTotalCount();

            final List<T> page = getPage.apply(dto);

            return new JSurbtcPaginatedList<T>(page, index -> {
                final String nextPath = appendPageParameter(path, index + 1);
                return get(nextPath, (__1, responseBody1) -> getPage.apply(json.parse(responseBody1, dtoType)));
            }, totalPages, totalCount);
        });
    }

    private static String appendPageParameter(final String path, final int page) {
        final boolean append = path.contains("?");
        return format("%s%spage=%d", path, append ? "&" : "?", page);
    }

    private void checkOrderId(final long orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException(format("invalid order id: %d", orderId));
        }
    }

    private <T, K> K get(final String path, final Class<T> valueType, final Function<T, K> mapper) throws Exception {
        return get(path, (__, responseBody) -> mapper.apply(json.parse(responseBody, valueType)));
    }

    private <T, K> K get(final String path, final HTTPClient.HTTPResponseHandler<K> responseHandler) throws Exception {
        return httpClient.get(path, handlingErrors(responseHandler));
    }

    private <T> T post(final String path, final Object payload, final HTTPClient.HTTPResponseHandler<T> responseHandler) throws Exception {
        return httpClient.post(path, json.payload(payload), handlingErrors(responseHandler));
    }

    private <T> HTTPClient.HTTPResponseHandler<T> parser(final Class<T> valueType) {
        return parser(valueType, Function.identity());
    }

    private <T, K> HTTPClient.HTTPResponseHandler<K> parser(final Class<T> valueType, final Function<T, K> mapper) {
        return (statusCode, responseBody) -> mapper.apply(json.parse(responseBody, valueType));
    }

    private <T> HTTPClient.HTTPResponseHandler<T> handlingErrors(final HTTPClient.HTTPResponseHandler<T> responseHandler) {
        return (statusCode, responseBody) -> {
            // OK(200) or CREATED(201)
            final boolean successful = statusCode == 200 || statusCode == 201;
            if (!successful) {
                final ExceptionDTO exceptionDTO = json.parse(responseBody, ExceptionDTO.class);
                if (null == exceptionDTO) {
                    throw new Exception(format("Surbtc request failed. status code: '%d' response body: '%s'", statusCode, responseBody));
                }

                throw error2Error(statusCode, exceptionDTO);
            }

            return responseHandler.handle(statusCode, responseBody);
        };
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