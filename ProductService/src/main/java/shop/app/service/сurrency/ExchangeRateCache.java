package shop.app.service.сurrency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import shop.app.entity.CurrencyRates;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExchangeRateCache {
    @Value("${currency-service.methods.get-currency}")
    public String uri;
    @Value("${currency-service.retry}")
    public int retry;
    private final WebClient webClient;

    @Cacheable(value = "currencyRates")
    public Mono<CurrencyRates> getCurrencyRates() {
        return webClient
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(CurrencyRates.class)
                .retryWhen(Retry.fixedDelay(retry, Duration.ofSeconds(2))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) ->
                                new RuntimeException("Currency service is unavailable after retries")))
                .doOnError(e -> log.error("Failed to fetch currency rates", e));
    }
}
