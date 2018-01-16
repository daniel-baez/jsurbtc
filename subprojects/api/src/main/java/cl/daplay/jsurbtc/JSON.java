package cl.daplay.jsurbtc;

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

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public interface JSON {

    String newAPIKey(String name, Instant expiration) throws IOException;

    String newOrder(String marketId,
                    String orderType,
                    String orderPriceType,
                    BigDecimal qty,
                    BigDecimal price) throws IOException;

    String cancelOrder(long orderId) throws IOException;


    ApiKey apiKey(String json) throws IOException;

    Order order(String json) throws IOException;

    Ticker ticker(String json) throws IOException;

    OrderBook orderBook(String json) throws IOException;

    Balance balance(String json) throws IOException;

    Trades trades(String json) throws IOException;

    List<Market> markets(String json) throws IOException;

    List<Balance> balances(String json) throws IOException;

    List<Order> orders(String json) throws IOException;

    List<Deposit> deposits(String json) throws IOException;

    List<Withdrawal> withdrawls(String json) throws IOException;

    Page page(String json) throws IOException;

    JSurbtcException exception(int statusCode, String json) throws Exception;
}
