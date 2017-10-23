package cl.daplay.jsurbtc.model.market;

import cl.daplay.jsurbtc.model.Currency;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.groupingBy;

public enum MarketID {
    BTC_CLP(Currency.BTC, Currency.CLP),
    BTC_COP(Currency.BTC, Currency.COP),
    BTC_PEN(Currency.BTC, Currency.PEN),
    ETH_BTC(Currency.ETH, Currency.BTC),
    ETH_CLP(Currency.ETH, Currency.CLP),
    ETH_COP(Currency.ETH, Currency.COP),
    ETH_PEN(Currency.ETH, Currency.PEN);

    private static final Map<Currency, List<MarketID>> MARKETS_BY_QUOTE_CURRENCY = Arrays.stream(MarketID.values())
            .collect(groupingBy(MarketID::getQuoteCurrency));

    private static final Map<Currency, List<MarketID>> MARKETS_BY_BASE_CURRENCY = Arrays.stream(MarketID.values())
            .collect(groupingBy(MarketID::getBaseCurrency));

    @JsonCreator
    public static MarketID fromJsonString(final String value) {
        return MarketID.valueOf(value.replace('-', '_'));
    }

    public static Optional<MarketID> byBaseAndQuoteCurrencies(final Currency base, final Currency quote) {
        return byBaseCurrency(base).stream().filter(marketId -> marketId.getQuoteCurrency() == quote).findFirst();
    }

    public static List<MarketID> byQuoteCurrency(final Currency quoteCurrency) {
        return MARKETS_BY_QUOTE_CURRENCY.getOrDefault(quoteCurrency, emptyList());
    }

    public static List<MarketID> byBaseCurrency(final Currency baseCurrency) {
        return MARKETS_BY_BASE_CURRENCY.getOrDefault(baseCurrency, emptyList());
    }

    private final Currency baseCurrency;
    private final Currency quoteCurrency;

    MarketID(final Currency baseCurrency, final Currency quoteCurrency) {
        this.baseCurrency = baseCurrency;
        this.quoteCurrency = quoteCurrency;
    }

    public Currency getBaseCurrency() {
        return baseCurrency;
    }

    public Currency getQuoteCurrency() {
        return quoteCurrency;
    }

    @Override
    @JsonValue
    public String toString() {
        return this.name().replace('_', '-');
    }

}
