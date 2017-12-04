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

public interface JSurbtc {

    static LongSupplier newNonce() {
        final long now = System.currentTimeMillis();
        final AtomicLong seed = new AtomicLong(now);
        return seed::getAndIncrement;
    }

    ApiKey newAPIKey(String name, Instant expiration) throws Exception;

    Order newOrder(MarketID marketId, OrderType orderType, OrderPriceType orderPriceType, BigDecimal qty, BigDecimal price) throws Exception;

    Trades getTrades(MarketID marketId, Instant timestamp) throws Exception;

    Trades getTrades(MarketID marketId) throws Exception;

    Order cancelOrder(long orderId) throws Exception;

    List<? extends Market> getMarkets() throws Exception;

    Ticker getTicker(MarketID marketId) throws Exception;

    OrderBook getOrderBook(MarketID marketId) throws Exception;

    Balance getBalance(Currency currency) throws Exception;

    List<? extends Balance> getBalances() throws Exception;

    List<? extends Order> getOrders(MarketID marketId) throws Exception;

    List<? extends Order> getOrders(MarketID marketId, OrderState orderState) throws Exception;

    List<? extends Order> getOrders(MarketID marketId, BigDecimal minimunExchanged) throws Exception;

    List<? extends Order> getOrders(MarketID marketId, OrderState orderState, BigDecimal minimunExchanged) throws Exception;

    Order getOrder(long orderId) throws Exception;

    List<? extends Deposit> getDeposits(Currency currency) throws Exception;

    List<? extends Withdrawal> getWithdrawals(Currency currency) throws Exception;

    List<? extends BalanceEvent> getBalanceEvents(Currency currency) throws Exception;
}
