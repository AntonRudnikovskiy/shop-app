package shop.app.service.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import shop.app.entity.CurrencyType;
import shop.app.exception.UnsupportedCurrencyException;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CurrencyProvider {
    private String currencyCode = "RUB";

    public void setCurrencyCode(String currencyCode) {
        if (!CurrencyType.isCurrencyValid(currencyCode)) {
            throw new UnsupportedCurrencyException("Invalid currency code: " + currencyCode);
        }
        this.currencyCode = currencyCode;
    }
}
