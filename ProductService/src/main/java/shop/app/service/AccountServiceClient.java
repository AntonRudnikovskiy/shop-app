package shop.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import shop.app.config.properties.AccountServiceProperties;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceClient {
    private final WebClient accountServiceWebClient;
    private final AccountServiceProperties properties;

    public CompletableFuture<Map<String, Integer>> getCustomerAccountNumber(List<String> login) {
        return accountServiceWebClient
                .post()
                .uri(properties.getHost() + properties.getGetAccount())
                .bodyValue(login)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Integer>>() {})
                .retry(2)
                .toFuture();
    }
}
