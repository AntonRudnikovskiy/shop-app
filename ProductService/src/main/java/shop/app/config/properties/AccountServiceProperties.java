package shop.app.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "webclient.account-service")
public class AccountServiceProperties {
    private String host;
    private String getAccount;
    private boolean isEnabled;
}