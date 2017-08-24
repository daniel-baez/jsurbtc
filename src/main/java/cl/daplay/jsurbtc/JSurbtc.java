package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.dto.*;
import cl.daplay.jsurbtc.dto.request.APIKeyRequestDTO;
import cl.daplay.jsurbtc.dto.request.OrderRequestDTO;
import cl.daplay.jsurbtc.dto.request.QuotationRequestDTO;
import cl.daplay.jsurbtc.model.APIKey;
import cl.daplay.jsurbtc.model.Currency;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.balance.Balance;
import cl.daplay.jsurbtc.model.balance.BalanceEvent;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.market.MarketID;
import cl.daplay.jsurbtc.model.order.*;
import cl.daplay.jsurbtc.model.quotation.Quotation;
import cl.daplay.jsurbtc.model.quotation.QuotationType;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongSupplier;

import static cl.daplay.jsurbtc.Constants.newBigDecimalFormat;
import static java.lang.String.format;
import static java.util.Collections.singletonMap;

public final class JSurbtc {

    static ObjectMapper buildObjectMapper() {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        return new ObjectMapper()
                .setDateFormat(simpleDateFormat)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    private final DecimalFormat bigDecimalFormat;
    private final ObjectMapper objectMapper;
    private final HttpLayer httpLayer;

    public JSurbtc(final String key, final String secret) {
        this(buildObjectMapper(), key, secret, System::currentTimeMillis, null);
    }

    public JSurbtc(final String key, final String secret, final HttpHost proxy) {
        this(buildObjectMapper(), key, secret, System::currentTimeMillis, proxy);
    }

    public JSurbtc(final ObjectMapper objectMapper, final String key, final String secret, final LongSupplier nonceSupplier, final HttpHost proxy) {
        this(objectMapper, HttpLayer.newInstance(key, secret, nonceSupplier, proxy), newBigDecimalFormat());
    }

    JSurbtc(final ObjectMapper objectMapper, final HttpLayer httpLayer, final DecimalFormat bigDecimalFormat) {
        this.httpLayer = httpLayer;
        this.objectMapper = objectMapper;
        this.bigDecimalFormat = bigDecimalFormat;
    }

    public APIKey createApiKey(final String name, final Instant expiration) throws Exception {
        final String path = "/api/v2/api_keys";
        final APIKeyRequestDTO payload = new APIKeyRequestDTO(name, expiration);
        return parseResponse(httpLayer.post(path, objectMapper.writeValueAsString(payload)), APIKey.class);
    }

    public Order createOrder(final MarketID marketId, final OrderType orderType, final OrderPriceType orderPriceType, final BigDecimal qty, final BigDecimal price) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        final OrderRequestDTO payload = new OrderRequestDTO(bigDecimalFormat, orderType, orderPriceType, qty, price);
        return parseResponse(httpLayer.post(path, objectMapper.writeValueAsString(payload)), OrderDTO.class, OrderDTO::getOrder);
    }

    public Order cancelOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId);
        return parseResponse(httpLayer.put(path, objectMapper.writeValueAsString(singletonMap("state", OrderState.CANCELING))), OrderDTO.class, OrderDTO::getOrder);
    }

    public List<Market> getMarkets() throws Exception {
        final String path = "/api/v2/markets";
        return parseResponse(httpLayer.get(path), MarketsDTO.class, MarketsDTO::getMarkets);
    }

    public Ticker getTicker(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/ticker", marketId).toLowerCase();
        return parseResponse(httpLayer.get(path), TickerDTO.class, TickerDTO::getTicker);
    }

    public OrderBook getOrderBook(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/order_book", marketId).toLowerCase();
        return parseResponse(httpLayer.get(path), OrderBookDTO.class, OrderBookDTO::getOrderBook);
    }

    public Balance getBalance(final Currency currency) throws Exception {
        final String path = format("/api/v2/balances/%s", currency).toLowerCase();
        return parseResponse(httpLayer.get(path), BalanceDTO.class, BalanceDTO::getBalance);
    }

    public List<Balance> getBalances() throws Exception {
        final String path = "/api/v2/balances";
        return parseResponse(httpLayer.get(path), BalancesDTO.class, BalancesDTO::getBalances);
    }

    public List<Order> getOrders(final MarketID marketId) throws Exception {
        final String path = format("/api/v2/markets/%s/orders", marketId).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public List<Order> getOrders(final MarketID marketId, final OrderState orderState) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&algo=", marketId, orderState).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public List<Order> getOrders(final MarketID marketId, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?minimun_exchanged=%s", marketId, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public List<Order> getOrders(final MarketID marketId, final OrderState orderState, final BigDecimal minimunExchanged) throws Exception {
        final String path = format("/api/v2/markets/%s/orders?state=%s&minimun_exchanged=%s", marketId, orderState, bigDecimalFormat.format(minimunExchanged)).toLowerCase();
        return newPaginatedList(path, OrdersDTO.class, OrdersDTO::getPagination, OrdersDTO::getOrders);
    }

    public Order getOrder(final long orderId) throws Exception {
        checkOrderId(orderId);
        final String path = format("/api/v2/orders/%d", orderId).toLowerCase();
        return parseResponse(httpLayer.get(path), OrderDTO.class, OrderDTO::getOrder);
    }

    public List<Deposit> getDeposits(final Currency currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/deposits", currency).toLowerCase();
        return newPaginatedList(path, DepositsDTO.class, DepositsDTO::getPagination, DepositsDTO::getDeposits);
    }

    public List<Withdrawal> getWithdrawals(final Currency currency) throws Exception {
        final String path = format("/api/v2/currencies/%s/withdrawals", currency).toLowerCase();
        return newPaginatedList(path, WithdrawalsDTO.class, WithdrawalsDTO::getPagination, WithdrawalsDTO::getWithdrawals);
    }

    public Quotation getQuotation(final MarketID marketId, final QuotationType quotationType, BigDecimal amount) throws Exception {
        final String path = format("/api/v2/markets/%s/quotations", marketId).toLowerCase();
        final QuotationRequestDTO requestDTO = new QuotationRequestDTO(quotationType, amount);

        return parseResponse(httpLayer.post(path, objectMapper.writeValueAsString(requestDTO)), QuotationDTO.class, QuotationDTO::getQuotation);
    }

    public List<BalanceEvent> getBalanceEvents(final Currency currency) throws Exception {
        final String path = "/api/v2/balance_events";
        final String params = "?currencies%5B%5D=" + currency + "&event_names%5B%5D=deposit_confirm&event_names%5B%5D=withdrawal_confirm&event_names%5B%5D=transaction&event_names%5B%5D=transfer_confirmation&relevant=true";
        return parseResponse(httpLayer.get(path + params), BalanceEventsDTO.class, BalanceEventsDTO::getBalanceEvents);
    }

    private <T, K> JSurbtcPaginatedList<T> newPaginatedList(String path,
                                                            Class<K> dtoType,
                                                            Function<K, PaginationDTO> getPagination,
                                                            Function<K, List<T>> getPage) throws Exception {
        return parseResponse(httpLayer.get(path), dtoType, dto -> {
            final PaginationDTO pagination = getPagination.apply(dto);
            final List<T> firstPage = getPage.apply(dto);

            return new JSurbtcPaginatedList(firstPage,
                    pagination.getTotalCount(),
                    pagination.getTotalPages(),
                    getNextPageFunction(path, dtoType, getPage));
        });
    }

    private static String appendPageParameter(final String path, final int page) {
        final boolean append = path.contains("?");
        return format("%s%spage=%d", path, append ? "&" : "?", page);
    }

    private <K, T> IntFunction<List<T>> getNextPageFunction(final String path, final Class<K> dtoType, final Function<K, List<T>> getPage) {
        return page -> {
            // pages at Surbtc start at 1
            final String nextPath = appendPageParameter(path, page + 1);

            try {
                return parseResponse(httpLayer.get(nextPath), dtoType, getPage);
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

        final boolean successful = statusCode == HttpStatus.SC_OK || statusCode == HttpStatus.SC_CREATED;
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
