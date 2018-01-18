package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.fun.ThrowingFunction;
import cl.daplay.jsurbtc.http.DefaultHTTPClient;
import cl.daplay.jsurbtc.http.RetryHTTPClient;
import cl.daplay.jsurbtc.jackson.JacksonJSON;
import cl.daplay.jsurbtc.lazylist.LazyList;
import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.Page;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.Balance;
import cl.daplay.jsurbtc.model.Deposit;
import cl.daplay.jsurbtc.model.Market;
import cl.daplay.jsurbtc.model.Order;
import cl.daplay.jsurbtc.model.OrderBook;
import cl.daplay.jsurbtc.model.Trades;
import cl.daplay.jsurbtc.model.Withdrawal;
import cl.daplay.jsurbtc.signer.DefaultSigner;
import cl.daplay.jsurbtc.signer.DoNothingSigner;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;
import java.util.logging.Logger;

import static cl.daplay.jsurbtc.Constants.newBigDecimalFormat;
import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;

public class JSurbtc implements Surbtc {

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
    private final static int HTTP_MAX_RETRY = Integer.parseInt(System.getProperty("JSURBTC.HTTP_MAX_RETRY", "5"), 10);

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
        this(key, secret, nonceSupplier, JacksonJSON.INSTANCE, httpProxy == null ? null : new Proxy(Proxy.Type.HTTP, httpProxy), httpMaxRetry);
    }

    public JSurbtc(final String key, final String secret, final LongSupplier nonceSupplier, final JacksonJSON json, final Proxy proxy, int httpMaxRetry) {
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

    @Override
    public ApiKey newAPIKey(final String name, final Instant expiration) throws Exception {
        final String path = "/api/v2/api_keys";

        return httpClient.post(path, defaultSigner, json.newAPIKey(name, expiration), responseHandler(json::apiKey));
    }

    @Override
    public Order newOrder(final String marketId, final String orderType, final String orderPriceType, final BigDecimal qty, final BigDecimal price) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        final String payload = json.newOrder(marketId, orderType, orderPriceType, qty, price);

        return httpClient.post(path, defaultSigner, payload, responseHandler(json::order));
    }

    @Override
    public Trades getTrades(final String marketId) throws Exception {
        return getTrades(marketId, null);
    }

    @Override
    public Trades getTrades(final String marketId, final Instant timestamp) throws Exception {
        String path = format("/api/v2/markets/%s/trades", marketId).toLowerCase();

        if (timestamp != null) {
            path += "?timestamp=" + timestamp.toEpochMilli();
        }

        return httpClient.get(path, noSignatureSigner, responseHandler(json::trades));
    }

    @Override
    public Order cancelOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId);

        String payload = json.cancelOrder(orderId);

        return httpClient.put(path, defaultSigner, payload, responseHandler(json::order));
    }

    @Override
    public List<Market> getMarkets() throws Exception {
        final String path = "/api/v2/markets";
        return httpClient.get(path, noSignatureSigner, responseHandler(json::markets));
    }

    @Override
    public Ticker getTicker(final String marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/ticker", marketId).toLowerCase();
        return httpClient.get(path, noSignatureSigner, responseHandler(json::ticker));
    }

    @Override
    public OrderBook getOrderBook(final String marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/order_book", marketId).toLowerCase();
        return httpClient.get(path, noSignatureSigner, responseHandler(json::orderBook));
    }

    @Override
    public Balance getBalance(final String currency) throws Exception {
        final String path = format("/api/v2/balances/%s", currency).toLowerCase();
        return httpClient.get(path, defaultSigner, responseHandler(json::balance));
    }

    @Override
    public List<Balance> getBalances() throws Exception {
        return httpClient.get("/api/v2/balances", defaultSigner, responseHandler(json::balances));
    }

    @Override
    public List<Order> getOrders(final String marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        return newPaginatedList(path, defaultSigner, json::orders);
    }

    @Override
    public List<Order> getOrders(final String marketId, final String orderState) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&algo=", marketId, orderState).toLowerCase();
        return newPaginatedList(path, defaultSigner, json::orders);
    }

    @Override
    public List<Order> getOrders(final String marketId, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?minimun_exchanged=%s", marketId, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, defaultSigner, json::orders);
    }

    @Override
    public List<Order> getOrders(final String marketId, final String orderState, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&minimun_exchanged=%s", marketId, orderState, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, defaultSigner, json::orders);
    }

    @Override
    public Order getOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId).toLowerCase();
        return httpClient.get(path, defaultSigner, responseHandler(json::order));
    }

    @Override
    public List<Deposit> getDeposits(final String currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/deposits", currency).toLowerCase();
        return newPaginatedList(path, defaultSigner, json::deposits);
    }

    @Override
    public List<Withdrawal> getWithdrawals(final String currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/withdrawals", currency).toLowerCase();
        return newPaginatedList(path, defaultSigner, json::withdrawls);
    }

    @Override
    public String getVersion() {
        return VERSION_SUPPLIER.get();
    }

    // ** implementation methods **

    private <T> LazyList<T> newPaginatedList(String path,
                                             Signer signer,
                                             ThrowingFunction<String, List<T>> parseList) throws Exception {
        return httpClient.get(path, signer, responseHandler((responseBody) -> {
            final List<T> page = parseList.apply(responseBody);
            final Page pagination = json.page(responseBody);

            final int totalPages = pagination.getTotalPages();
            final int totalCount = pagination.getTotalCount();

            return new LazyList<>(page, index -> {
                final boolean append = path.contains("?");
                final String nextPath = format("%s%spage=%d", path, append ? "&" : "?", index + 1);

                return httpClient.get(nextPath, signer, responseHandler(parseList));
            }, totalPages, totalCount);
        }));
    }

    private void checkOrderId(final long orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException(format("invalid order id: %d", orderId));
        }
    }

    private <T> HTTPClient.HTTPResponseHandler<T> responseHandler(final ThrowingFunction<String, T> mapper) {
        return (statusCode, responseBody) -> {
            // OK(200) or CREATED(201)
            final boolean successful = statusCode == 200 || statusCode == 201;
            if (!successful) {
                throw json.exception(statusCode, responseBody);
            }

            return mapper.apply(responseBody);
        };
    }

}
