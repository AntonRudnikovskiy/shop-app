package shop.app.service.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import shop.app.entity.CurrencyRates;
import shop.app.entity.CurrencyType;
import shop.app.utils.JsonMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {
    @Value("${exchange-rate.file-name}")
    private String fileName;
    @Value("${exchange-rate.base-rate}")
    private BigDecimal baseRate;
    private final JsonMapper jsonMapper;
    private final CurrencyProvider currencyProvider;
    private final ExchangeRateCache exchangeRateCache;

    public Pair<String, BigDecimal> getExchangeRate(BigDecimal price) {
        String providerCurrency = currencyProvider.getCurrencyCode();
        if (providerCurrency.equals(CurrencyType.RUB.name())) {
            return Pair.of(CurrencyType.RUB.name(), price);
        }
        BigDecimal convertedPrice = convertCurrency(price, baseRate, fetchCurrencyRates().getRates()
                .get(providerCurrency));
        return Pair.of(providerCurrency, convertedPrice);
    }

    private CurrencyRates fetchCurrencyRates() {
        try {
            return exchangeRateCache.getCurrencyRates()
                    .onErrorResume(e -> {
                        log.warn("Falling back to local file for currency rates");
                        return Mono.just(jsonMapper.readJsonAsObject(fileName, CurrencyRates.class));
                    })
                    .block();
        } catch (Exception e) {
            log.error("Failed to fetch currency rates, using local file as fallback", e);
            return jsonMapper.readJsonAsObject(fileName, CurrencyRates.class);
        }
    }

    public static BigDecimal convertCurrency(BigDecimal amount, BigDecimal baseRate, BigDecimal targetRate) {
        if (baseRate.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Base rate cannot be zero");
        }
        return amount.multiply(targetRate).divide(baseRate, 2, RoundingMode.HALF_UP);
    }
}
