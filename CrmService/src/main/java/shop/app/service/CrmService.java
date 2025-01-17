package shop.app.service;

import java.util.List;
import java.util.Map;

public interface CrmService {
    Map<String, Integer> getCustomerInn(List<String> login);
}