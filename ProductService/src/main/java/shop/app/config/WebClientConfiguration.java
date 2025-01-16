package shop.app.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;
import shop.app.config.properties.AccountServiceProperties;
import shop.app.config.properties.CrmServiceProperties;
import shop.app.config.properties.CurrencyServiceProperties;
import shop.app.config.properties.CustomerServiceProperties;

import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {
    public final int timeout = 5000;
    private final AccountServiceProperties accountServiceProperties;
    private final CrmServiceProperties crmServiceProperties;
    private final CurrencyServiceProperties currencyServiceProperties;
    private final CustomerServiceProperties customerServiceProperties;

    @Bean
    public WebClient accountServiceWebClient() {
        return getWebClient(accountServiceProperties.getHost());
    }

    @Bean
    public WebClient crmServiceWebClient() {
        return getWebClient(crmServiceProperties.getHost());
    }

    @Bean
    public WebClient currencyServiceWebClient() {
        return getWebClient(currencyServiceProperties.getHost());
    }

    @Bean
    public WebClient customerServiceWebClient() {
        return getWebClient(customerServiceProperties.getHost());
    }

    private WebClient getWebClient(String host) {
        final var tcpClient = TcpClient
                .create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeout, TimeUnit.MILLISECONDS));
                });

        return WebClient.builder()
                .baseUrl(host)
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .build();
    }
}
