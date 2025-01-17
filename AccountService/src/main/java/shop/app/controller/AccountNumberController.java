package shop.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.app.service.AccountNumberService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AccountNumberController {
    private final AccountNumberService accountNumberService;

    @PostMapping("/account")
    public ResponseEntity<Map<String, Integer>> getCustomerAccountNumber(@RequestBody List<String> login) {
        return ResponseEntity.ok(accountNumberService.getCustomerAccountNumber(login));
    }
}
