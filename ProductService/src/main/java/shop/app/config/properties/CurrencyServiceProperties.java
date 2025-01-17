package shop.app.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "webclient.currency-service")
public class CurrencyServiceProperties {
    private String host;
    private String getCurrency;
    private boolean isEnabled;
}
