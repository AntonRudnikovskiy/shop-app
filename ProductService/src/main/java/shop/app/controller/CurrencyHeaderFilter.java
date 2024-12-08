package shop.app.controller;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.app.service.currency.CurrencyProvider;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyHeaderFilter extends OncePerRequestFilter {
    private final CurrencyProvider currencyProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currencyHeader = request.getHeader("currency");
        if (currencyHeader != null && !currencyHeader.isBlank()) {
            currencyProvider.setCurrencyCode(currencyHeader);
        }
        filterChain.doFilter(request, response);
    }
}
