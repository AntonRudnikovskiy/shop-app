package shop.app.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "webclient.crm-service")
public class CrmServiceProperties {
    private String host;
    private String getCrm;
    private boolean isEnabled;
}