package cl.daplay.jsurbtc;

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
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongSupplier;

import static java.lang.System.currentTimeMillis;

/**
 * Main entrypoint for the client
 */
public interface JSurbtc {

    /**
     * default nonce implementation, can't be shared among client
     *
     * @return
     */
    static LongSupplier newNonce() {
        return new AtomicLong(currentTimeMillis())::getAndIncrement;
    }

    ApiKey newAPIKey(String name, Instant expiration) throws Exception;

    Order newOrder(MarketID marketId,
                   OrderType orderType,
                   OrderPriceType orderPriceType,
                   BigDecimal qty,
                   BigDecimal price) throws Exception;

    Trades getTrades(MarketID marketId, Instant timestamp) throws Exception;

    Trades getTrades(MarketID marketId) throws Exception;

    Order cancelOrder(long orderId) throws Exception;

    List<Market> getMarkets() throws Exception;

    Ticker getTicker(MarketID marketId) throws Exception;

    OrderBook getOrderBook(MarketID marketId) throws Exception;

    Balance getBalance(Currency currency) throws Exception;

    List<Balance> getBalances() throws Exception;

    List<Order> getOrders(MarketID marketId) throws Exception;

    List<Order> getOrders(MarketID marketId,
                          OrderState orderState) throws Exception;

    List<Order> getOrders(MarketID marketId,
                          BigDecimal minimunExchanged) throws Exception;

    List<Order> getOrders(MarketID marketId,
                          OrderState orderState,
                          BigDecimal minimunExchanged) throws Exception;

    Order getOrder(long orderId) throws Exception;

    List<Deposit> getDeposits(Currency currency) throws Exception;

    List<Withdrawal> getWithdrawals(Currency currency) throws Exception;

    List<BalanceEvent> getBalanceEvents(Currency currency) throws Exception;
}
