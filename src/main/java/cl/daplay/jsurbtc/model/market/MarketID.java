package cl.daplay.jsurbtc.model.market;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.*;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

public enum MarketID implements Iterable<String> {
    BTC_CLP("BTC", "CLP"),
    BTC_COP("BTC", "COP"),
    BTC_PEN("BTC", "PEN"),
    ETH_BTC("ETH", "BTC"),
    ETH_CLP("ETH", "CLP"),
    ETH_COP("ETH", "COP"),
    ETH_PEN("ETH", "PEN"),
    BCH_CLP("BCH", "CLP"),
    BCH_COP("BCH", "COP"),
    BCH_PEN("BCH", "PEN"),
    BCH_BTC("BCH", "BTC"),
    ;

    private static final Map<String, List<MarketID>> MARKETS_BY_QUOTE_CURRENCY = Arrays.stream(MarketID.values())
            .collect(groupingBy(MarketID::getQuoteCurrency));

    private static final Map<String, List<MarketID>> MARKETS_BY_BASE_CURRENCY = Arrays.stream(MarketID.values())
            .collect(groupingBy(MarketID::getBaseCurrency));

    @JsonCreator
    public static MarketID fromJsonString(final String value) {
        return MarketID.valueOf(value.replace('-', '_'));
    }

    public static Optional<MarketID> byBaseAndQuoteCurrencies(final String base, final String quote) {
        return byBaseCurrency(base).stream().filter(marketId -> marketId.getQuoteCurrency() == quote).findFirst();
    }

    public static List<MarketID> byQuoteCurrency(final String quoteCurrency) {
        return MARKETS_BY_QUOTE_CURRENCY.getOrDefault(quoteCurrency, emptyList());
    }

    public static List<MarketID> byBaseCurrency(final String baseCurrency) {
        return MARKETS_BY_BASE_CURRENCY.getOrDefault(baseCurrency, emptyList());
    }

    private final String baseCurrency;
    private final String quoteCurrency;

    MarketID(final String baseCurrency, final String quoteCurrency) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.asList(baseCurrency, quoteCurrency).iterator();
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name().replace('_', '-');
    }

}
