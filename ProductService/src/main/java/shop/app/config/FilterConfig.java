package shop.app.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shop.app.controller.CurrencyHeaderFilter;
import shop.app.service.сurrency.CurrencyProvider;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CurrencyHeaderFilter> filterFilterRegistrationBean(CurrencyProvider currencyProvider) {
        FilterRegistrationBean<CurrencyHeaderFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CurrencyHeaderFilter(currencyProvider));
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
