package cl.daplay.jsurbtc;

import cl.daplay.jsurbtc.model.ApiKey;
import cl.daplay.jsurbtc.model.Ticker;
import cl.daplay.jsurbtc.model.balance.Balance;
import cl.daplay.jsurbtc.model.deposit.Deposit;
import cl.daplay.jsurbtc.model.market.Market;
import cl.daplay.jsurbtc.model.order.Order;
import cl.daplay.jsurbtc.model.order.OrderBook;
import cl.daplay.jsurbtc.model.trades.Trades;
import cl.daplay.jsurbtc.model.withdrawal.Withdrawal;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface Surbtc {

    ApiKey newAPIKey(String name, Instant expiration) throws Exception;

    Order newOrder(String marketId, String orderType, String orderPriceType, BigDecimal qty, BigDecimal price) throws Exception;

    Trades getTrades(String marketId) throws Exception;

    Trades getTrades(String marketId, Instant timestamp) throws Exception;

    Order cancelOrder(long orderId) throws Exception;

    List<Market> getMarkets() throws Exception;

    Ticker getTicker(String marketId) throws Exception;

    OrderBook getOrderBook(String marketId) throws Exception;

    Balance getBalance(String currency) throws Exception;

    List<Balance> getBalances() throws Exception;

    List<Order> getOrders(String marketId) throws Exception;

    List<Order> getOrders(String marketId, String orderState) throws Exception;

    List<Order> getOrders(String marketId, BigDecimal minimunExchanged) throws Exception;

    List<Order> getOrders(String marketId, String orderState, BigDecimal minimunExchanged) throws Exception;

    Order getOrder(long orderId) throws Exception;

    List<Deposit> getDeposits(String currency) throws Exception;

    List<Withdrawal> getWithdrawals(String currency) throws Exception;

    String getVersion();
}
