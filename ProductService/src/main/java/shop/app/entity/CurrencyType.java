package shop.app.entity;

import java.util.Arrays;

public enum CurrencyType {
    RUB("RUB"),
    USD("USD"),
    EUR("EUR"),
    CNY("CNY");

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
}
