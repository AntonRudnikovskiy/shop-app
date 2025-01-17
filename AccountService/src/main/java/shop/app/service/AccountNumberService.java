package shop.app.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccountNumberService implements AccountService {

    @Override
    public Map<String, Integer> getCustomerAccountNumber(List<String> customerLogin) {
        Map<String, Integer> accountNumbers = Map.of(
                "login1", 3425345,
                "login2", 2343434,
                "login3", 4554566,
                "login4", 4545545
        );
        return customerLogin.stream()
                .collect(Collectors.toMap(l -> l, accountNumbers::get));
    }
}
