package shop.app.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CrmInnService implements CrmService {
    @Override
    public Map<String, Integer> getCustomerInn(List<String> customerLogin) {
        Map<String, Integer> accountNumbers = Map.of(
                "login1", 67878,
                "login2", 23426,
                "login3", 324234,
                "login4", 345456
        );
        return customerLogin.stream()
                .collect(Collectors.toMap(l -> l, accountNumbers::get));
    }
}