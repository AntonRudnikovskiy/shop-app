package shop.app.service.currency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Getter
@Setter
@Component
@SessionScope
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyProvider {
    private String currencyCode;
}
