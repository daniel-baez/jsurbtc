package cl.daplay.jsurbtc;

import static cl.daplay.jsurbtc.Constants.newBigDecimalFormat;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.stream;
import static java.util.Collections.singletonMap;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.LongSupplier;
import java.util.logging.Logger;

import cl.daplay.jsurbtc.http.DefaultHTTPClient;
import cl.daplay.jsurbtc.http.RetryHTTPClient;
import cl.daplay.jsurbtc.jackson.dto.ApiKeyDTO;
import cl.daplay.jsurbtc.jackson.dto.BalanceDTO;
import cl.daplay.jsurbtc.jackson.dto.BalancesDTO;
import cl.daplay.jsurbtc.jackson.dto.DepositsDTO;
import cl.daplay.jsurbtc.jackson.dto.ExceptionDTO;
import cl.daplay.jsurbtc.jackson.dto.MarketsDTO;
import cl.daplay.jsurbtc.jackson.dto.OrderBookDTO;
import cl.daplay.jsurbtc.jackson.dto.OrderDTO;
import cl.daplay.jsurbtc.jackson.dto.OrdersDTO;
import cl.daplay.jsurbtc.jackson.dto.PaginationDTO;
import cl.daplay.jsurbtc.jackson.dto.TickerDTO;
import cl.daplay.jsurbtc.jackson.dto.TradesDTO;
import cl.daplay.jsurbtc.jackson.dto.WithdrawalsDTO;
import cl.daplay.jsurbtc.jackson.dto.request.APIKeyRequestDTO;
import cl.daplay.jsurbtc.jackson.dto.request.OrderRequestDTO;
import cl.daplay.jsurbtc.lazylist.LazyList;
import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.balance.Balance;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.market.MarketID;
import cl.daplay.jsurbtc.model.order.Order;
import cl.daplay.jsurbtc.model.order.OrderBook;
import cl.daplay.jsurbtc.model.order.OrderPriceType;
import cl.daplay.jsurbtc.model.order.OrderState;
import cl.daplay.jsurbtc.model.order.OrderType;
import cl.daplay.jsurbtc.model.trades.Trades;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import cl.daplay.jsurbtc.signers.DefaultSigner;
import cl.daplay.jsurbtc.signers.DoNothingSigner;

/**
 * Main entrypoint
 */
public class JSurbtc {

    /**
     * @return default nonce implementation, can't be shared among client
     */
    public static LongSupplier newNonce() {
        return new AtomicLong(currentTimeMillis())::getAndIncrement;
    }

    private final static Logger LOGGER = Logger.getLogger(JSurbtc.class.getName());

    private final static VersionSupplier VERSION_SUPPLIER  = VersionSupplier.INSTANCE;

    /**
     * by default, this client will retry any HTTP error 5 times, returning the fifth Exception.
     *
     * You may customize this number by environment variable "JSURBTC.HTTP_MAX_RETRY"
     */
    private final static int HTTP_MAX_RETRY = Integer.parseInt(System.getProperty("JSURBTC.HTTP_MAX_RETRY", "5"));

    private final DecimalFormat bigDecimalFormat;
    private final HTTPClient httpClient;
    private final JSON json;
    private final Signer defaultSigner;
    private final Signer noSignatureSigner;

    public JSurbtc() {
        this(null, null, JSurbtc.newNonce(), null, HTTP_MAX_RETRY);
    }

    public JSurbtc(final String key, final String secret) {
        this(key, secret, JSurbtc.newNonce(), null, HTTP_MAX_RETRY);
    }

    public JSurbtc(final String key, final String secret, final LongSupplier nonceSupplier) {
        this(key, secret, nonceSupplier, null, HTTP_MAX_RETRY);
    }

    public JSurbtc(final String key, final String secret, final LongSupplier nonceSupplier, final InetSocketAddress httpProxy, int httpMaxRetry) {
        this(key, secret, nonceSupplier, JSON.INSTANCE, httpProxy == null ? null : new Proxy(Proxy.Type.HTTP, httpProxy), httpMaxRetry);
    }

    public JSurbtc(final String key, final String secret, final LongSupplier nonceSupplier, final JSON json, final Proxy proxy, int httpMaxRetry) {
        this(new RetryHTTPClient(new DefaultHTTPClient(proxy, key, nonceSupplier, VERSION_SUPPLIER.get()), httpMaxRetry),
                newBigDecimalFormat(), 
                json,
                new DefaultSigner(secret),
                DoNothingSigner.INSTANCE);
    }

    public JSurbtc(JSurbtc other) {
        this.bigDecimalFormat = other.bigDecimalFormat;
        this.httpClient = other.httpClient;
        this.json = other.json;
        this.defaultSigner = other.defaultSigner;
        this.noSignatureSigner = other.noSignatureSigner;
    }

    public JSurbtc(final HTTPClient httpClient,
            final DecimalFormat bigDecimalFormat,
            final JSON json,
            final Signer defaultSigner,
            final Signer noSignatureSigner) {
        this.bigDecimalFormat = bigDecimalFormat;
        this.httpClient = httpClient;
        this.json = json;
        this.defaultSigner = defaultSigner;
        this.noSignatureSigner = noSignatureSigner;
    }

    public ApiKey newAPIKey(final String name, final Instant expiration) throws Exception {
        final String path = "/api/v2/api_keys";
        return post(path, defaultSigner, new APIKeyRequestDTO(name, expiration), parser(ApiKeyDTO.class, ApiKeyDTO::getApiKey));
    }

    public Order newOrder(final MarketID marketId, final OrderType orderType, final OrderPriceType orderPriceType, final BigDecimal qty, final BigDecimal price) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        final OrderRequestDTO payload = new OrderRequestDTO(bigDecimalFormat, orderType, orderPriceType, qty, price);
        return post(path, defaultSigner, payload, parser(OrderDTO.class, OrderDTO::getOrder));
    }

    public Trades getTrades(final MarketID marketId, final Instant timestamp) throws Exception {
        String path = format("/api/v2/markets/%s/trades", marketId).toLowerCase();

        if (timestamp != null) {
            path += "?timestamp=" + timestamp.toEpochMilli();
        }

        return get(path, noSignatureSigner, parser(TradesDTO.class, TradesDTO::getTrades));
    }

    public String getVersion() {
        return VERSION_SUPPLIER.get();
    }

    public Trades getTrades(final MarketID marketId) throws Exception {
        return getTrades(marketId, null);
    }

    public Order cancelOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId);
        return httpClient.put(path, defaultSigner, json.payload(singletonMap("state", OrderState.CANCELING)), handlingErrors(parser(OrderDTO.class, OrderDTO::getOrder)));
    }

    public List<Market> getMarkets() throws Exception {
        final String path = "/api/v2/markets";
        return get(path, noSignatureSigner, MarketsDTO.class, MarketsDTO::getMarkets);
    }

    public Ticker getTicker(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/ticker", marketId).toLowerCase();
        return get(path, noSignatureSigner, TickerDTO.class, TickerDTO::getTicker);
    }

    public OrderBook getOrderBook(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/order_book", marketId).toLowerCase();
        return get(path, noSignatureSigner, OrderBookDTO.class, OrderBookDTO::getOrderBook);
    }

    public Balance getBalance(final String currency) throws Exception {
        final String path = format("/api/v2/balances/%s", currency).toLowerCase();
        return get(path, defaultSigner, BalanceDTO.class, BalanceDTO::getBalance);
    }

    public List<Balance> getBalances() throws Exception {
        return get("/api/v2/balances", defaultSigner, BalancesDTO.class, BalancesDTO::getBalances);
    }

    public List<Order> getOrders(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        return newPaginatedList(path, defaultSigner, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public List<Order> getOrders(final MarketID marketId, final OrderState orderState) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&algo=", marketId, orderState).toLowerCase();
        return newPaginatedList(path, defaultSigner, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public List<Order> getOrders(final MarketID marketId, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?minimun_exchanged=%s", marketId, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, defaultSigner, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public List<Order> getOrders(final MarketID marketId, final OrderState orderState, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&minimun_exchanged=%s", marketId, orderState, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, defaultSigner, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public Order getOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId).toLowerCase();
        return get(path, defaultSigner, OrderDTO.class, OrderDTO::getOrder);
    }

    public List<Deposit> getDeposits(final String currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/deposits", currency).toLowerCase();
        return newPaginatedList(path, defaultSigner, DepositsDTO.class, DepositsDTO::getPagination, DepositsDTO::getDeposits);
    }

    public List<Withdrawal> getWithdrawals(final String currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/withdrawals", currency).toLowerCase();
        return newPaginatedList(path, defaultSigner, WithdrawalsDTO.class, WithdrawalsDTO::getPagination, WithdrawalsDTO::getWithdrawals);
    }

    // ** implementation methods **

    private <T, K> LazyList<T> newPaginatedList(final String path,
                                                final Signer signer,
                                                final Class<K> dtoType,
                                                final Function<K, PaginationDTO> getPagination,
                                                final Function<K, List<T>> getPage) throws Exception {

        return get(path, signer, (__, responseBody) -> {
            final K dto = json.parse(responseBody, dtoType);

            final PaginationDTO pagination = getPagination.apply(dto);
            final int totalPages = pagination.getTotalPages();
            final int totalCount = pagination.getTotalCount();

            final List<T> page = getPage.apply(dto);

            return new LazyList<T>(page, index -> {
                final String nextPath = appendPageParameter(path, index + 1);
                return get(nextPath, signer, (__1, responseBody1) -> getPage.apply(json.parse(responseBody1, dtoType)));
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

    private <T, K> K get(final String path, Signer signer, final Class<T> valueType, final Function<T, K> mapper) throws Exception {
        return get(path, signer, (__, responseBody) -> mapper.apply(json.parse(responseBody, valueType)));
    }

    private <T, K> K get(final String path, Signer signer, final HTTPClient.HTTPResponseHandler<K> responseHandler) throws Exception {
        return httpClient.get(path, signer, handlingErrors(responseHandler));
    }

    private <T> T post(final String path, Signer signer, final Object payload, final HTTPClient.HTTPResponseHandler<T> responseHandler) throws Exception {
        return httpClient.post(path, signer, json.payload(payload), handlingErrors(responseHandler));
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
