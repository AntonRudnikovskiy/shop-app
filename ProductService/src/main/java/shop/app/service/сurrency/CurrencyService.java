package shop.app.service.сurrency;

import org.springframework.data.util.Pair;

import java.math.BigDecimal;

public interface CurrencyService {
    Pair<String, BigDecimal> getExchangeRate(BigDecimal price);
}
