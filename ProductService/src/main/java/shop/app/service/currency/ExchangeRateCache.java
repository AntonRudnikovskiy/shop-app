package shop.app.service.currency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import shop.app.entity.CurrencyRates;
import shop.app.exception.CurrencyServiceException;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateCache {
    @Value("${currency-service.methods.get-currency}")
    private String uri;
    @Value("${currency-service.retry}")
    private int retry;
    private final WebClient webClient;

    @Cacheable(value = "currencyRates")
    public Mono<CurrencyRates> getCurrencyRates() {
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,
                        response -> response.bodyToMono(String.class)
                                .map(body -> new CurrencyServiceException(
                                        "Currency service error: " + response.statusCode() + " " + body)))
                .bodyToMono(CurrencyRates.class)
                .retryWhen(Retry.fixedDelay(retry, Duration.ofSeconds(2))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new CurrencyServiceException("Currency service is unavailable after " + retry + " retries")))
                .doOnError(e -> log.error("Failed to fetch currency rates: {}", e.getMessage(), e));
    }
}
