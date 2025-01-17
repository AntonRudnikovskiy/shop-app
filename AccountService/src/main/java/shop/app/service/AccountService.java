package shop.app.service;

import java.util.List;
import java.util.Map;

public interface AccountService {
    Map<String, Integer> getCustomerAccountNumber(List<String> login);
}
