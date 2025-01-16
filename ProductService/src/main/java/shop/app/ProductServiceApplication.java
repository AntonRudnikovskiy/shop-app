package shop.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import shop.app.config.properties.AccountServiceProperties;
import shop.app.config.properties.CrmServiceProperties;
import shop.app.config.properties.CurrencyServiceProperties;
import shop.app.config.properties.CustomerServiceProperties;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableConfigurationProperties({
        AccountServiceProperties.class,
        CrmServiceProperties.class,
        CurrencyServiceProperties.class,
        CustomerServiceProperties.class
})
public class ProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class);
    }
}
