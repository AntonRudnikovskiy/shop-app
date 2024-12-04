package shop.app.dto.criteria;

import java.util.Arrays;

public enum Operation {
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN_OR_EQUAL("<="),
    LIKE_TEXT("~"),
    LIKE_NUMBER("$"),
    LIKE_DATA("-"),
    EQUAL("=");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public static Operation fromSymbol(String symbol) {
        return Arrays.stream(values())
                .filter(op -> op.symbol.equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operation: " + symbol));
    }
}

