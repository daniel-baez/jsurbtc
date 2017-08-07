package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.dto.*;
import cl.daplay.jsurbtc.dto.request.APIKeyRequestDTO;
import cl.daplay.jsurbtc.dto.request.OrderRequestDTO;
import cl.daplay.jsurbtc.http.SurbtcHttpRequestInterceptor;
import cl.daplay.jsurbtc.model.APIKey;
import cl.daplay.jsurbtc.model.Balance;
import cl.daplay.jsurbtc.model.Currency;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.market.MarketID;
import cl.daplay.jsurbtc.model.order.*;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongSupplier;

import static java.lang.String.format;
import static java.util.Collections.singletonMap;

public final class JSurbtc {

    private static final String BASE_PATH = "https://www.surbtc.com/";

    static String appendPageParameter(final String path, final int page) {
        final boolean append = path.contains("?");
        return format("%s%spage=%d", path, append ? "&" : "?", page);
    }

    static ObjectMapper buildObjectMapper() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return new ObjectMapper()
                .setDateFormat(simpleDateFormat)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    private static CloseableHttpClient buildHttpClient(String key, String secret, LongSupplier nonceSupplier) {
        return HttpClients.custom()
                .setProxy(new HttpHost("localhost", 8888))
                .addInterceptorFirst(new SurbtcHttpRequestInterceptor(key, secret, nonceSupplier))
                .build();
    }

    private final ObjectMapper objectMapper;
    private final CloseableHttpClient httpClient;

    public JSurbtc(String key, String secret) {
        this(buildObjectMapper(), key, secret, Nonce.INSTANCE);
    }

    public JSurbtc(ObjectMapper objectMapper, String key, String secret, LongSupplier nonceSupplier) {
        this.httpClient = buildHttpClient(key, secret, nonceSupplier);
        this.objectMapper = objectMapper;
    }

    public APIKey createApiKey(final String name, final Instant expiration) throws Exception {
        final String path = "/api/v2/api_keys";
        return parseResponse(post(path, new APIKeyRequestDTO(name, expiration)), APIKey.class);
    }

    public Order createOrder(final MarketID marketID, final OrderType orderType, final OrderPriceType orderPriceType, final BigDecimal qty, final BigDecimal price) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketID).toLowerCase();
        return parseResponse(post(path, new OrderRequestDTO(orderType, orderPriceType, qty, price)), OrderDTO.class, OrderDTO::getOrder);
    }

    public Order cancelOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId);
        return parseResponse(put(path, singletonMap("state", OrderState.CANCELING)), OrderDTO.class, OrderDTO::getOrder);
    }

    public List<Market> getMarkets() throws Exception {
        final String path = "/api/v2/markets";
        return parseResponse(get(path), MarketsDTO.class, MarketsDTO::getMarkets);
    }

    public Ticker getTicker(final MarketID marketID) throws Exception {
        final String path = format("/api/v2/markets/%s/ticker", marketID).toLowerCase();
        return parseResponse(get(path), TickerDTO.class, TickerDTO::getTicker);
    }

    public OrderBook getOrderBook(final MarketID marketID) throws Exception {
        final String path = format("/api/v2/markets/%s/order_book", marketID).toLowerCase();
        return parseResponse(get(path), OrderBookDTO.class, OrderBookDTO::getOrderBook);
    }

    public Balance getBalance(final Currency currency) throws Exception {
        final String path = format("/api/v2/balances/%s", currency).toLowerCase();
        return parseResponse(get(path), BalanceDTO.class, BalanceDTO::getBalance);
    }

    public List<Balance> getBalances() throws Exception {
        final String path = "/api/v2/balances";
        return parseResponse(get(path), BalancesDTO.class, BalancesDTO::getBalances);
    }

    public List<Order> getOrders(final MarketID marketID) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketID).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public List<Order> getOrders(final MarketID marketID, final OrderState orderState) throws Exception {
        /*
         * per	300	Numero de ordenes por página [min 1, max 300]
         * page	1	Numero de pagina a recibir
         * state	None	Estado de la orden
         * minimun_exchanged	None	Minimo transado por la orden
         */
        final String path = format("/api/v2/markets/%s/orders?state=%s&algo=", marketID, orderState).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public Order getOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId).toLowerCase();
        return parseResponse(get(path), OrderDTO.class, OrderDTO::getOrder);
    }

    public List<Deposit> getDeposits(final Currency currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/deposits", currency).toLowerCase();
        return newPaginatedList(path, DepositsDTO.class, DepositsDTO::getPagination, DepositsDTO::getDeposits);
    }

    public List<Withdrawal> getWithdrawals(final Currency currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/withdrawals", currency).toLowerCase();
        return newPaginatedList(path, WithdrawalsDTO.class, WithdrawalsDTO::getPagination, WithdrawalsDTO::getWithdrawals);
    }

    private CloseableHttpResponse get(final String path) throws Exception {
        final HttpGet get = new HttpGet(BASE_PATH + path);
        return httpClient.execute(get);
    }

    private CloseableHttpResponse put(final String path, final Object payload) throws Exception {
        final HttpPut post = new HttpPut(BASE_PATH + path);
        final StringEntity input = new StringEntity(objectMapper.writeValueAsString(payload));

        input.setContentType("application/json");
        post.setEntity(input);

        return httpClient.execute(post);
    }

    private CloseableHttpResponse post(final String path, final Object payload) throws Exception {
        final HttpPost post = new HttpPost(BASE_PATH + path);
        final StringEntity input = new StringEntity(objectMapper.writeValueAsString(payload));

        input.setContentType("application/json");
        post.setEntity(input);

        return httpClient.execute(post);
    }

    private <T, K> JSurbtcPaginatedList<T> newPaginatedList(String path,
                                                            Class<K> dtoType,
                                                            Function<K, PaginationDTO> getPagination,
                                                            Function<K, List<T>> getPage) throws Exception {
        return parseResponse(get(path), dtoType, dto -> {
            final PaginationDTO pagination = getPagination.apply(dto);
            final List<T> firstPage = getPage.apply(dto);

            return new JSurbtcPaginatedList(firstPage,
                    pagination.getTotalCount(),
                    pagination.getTotalPages(),
                    getNextPageFunction(path, dtoType, getPage));
        });
    }

    private <K, T> IntFunction<List<T>> getNextPageFunction(final String path, final Class<K> dtoType, final Function<K, List<T>> getPage) {
        return page -> {
            // pages at Surbtc start at 1
            final String nextPath = appendPageParameter(path, page + 1);

            try {
                return parseResponse(get(nextPath), dtoType, getPage);
            } catch (Exception ex) {
                throw new RuntimeException(format("while getting page:%d for path:%s", page, path), ex);
            }
        };
    }

    private <T, K> K parseResponse(final CloseableHttpResponse response, final Class<T> valueType) throws Exception {
        return parseResponse(response, valueType, (Function<T, K>) Function.identity());
    }

    private <T, K> K parseResponse(final CloseableHttpResponse response, final Class<T> valueType, final Function<T, K> mapper) throws Exception {
        final int statusCode = response.getStatusLine().getStatusCode();

        final boolean successful = statusCode == HttpStatus.SC_OK;
        if (!successful) {
            final ExceptionDTO exceptionDTO = objectMapper.readValue(response.getEntity().getContent(), ExceptionDTO.class);
            throw new Exception(format("Surbtc request failed. status code: %d, message: %s", statusCode, exceptionDTO.getMessage()));
        }

        final T t = objectMapper.readValue(response.getEntity().getContent(), valueType);
        return mapper.apply(t);
    }

    private void checkOrderId(final long orderId) {
        if (orderId <= 0) {
            throw new IllegalArgumentException(format("invalid order id: %d", orderId));
        }
    }

}
