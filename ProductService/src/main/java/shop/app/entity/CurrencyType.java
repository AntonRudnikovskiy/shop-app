package shop.app.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum CurrencyType {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    CNY("CNY");

    private static final Map<String, CurrencyType> CURRENCY_TYPE_MAP = Arrays.stream(values())
            .collect(Collectors.toMap(op -> op.currency, op -> op));
    private final String currency;

    CurrencyType(String currency) {
        this.currency = currency;
    }

    public static CurrencyType fromCurrency(String currency) {
        return Arrays.stream(values())
                .filter(op -> op.currency.equals(currency))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown currency: " + currency));
    }

    public static boolean isCurrencyValid(String currency) {
        return CURRENCY_TYPE_MAP.containsKey(currency);
    }
}
