//package shop.app;
//
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.boot.test.mock.mockito.SpyBean;
//import org.springframework.data.util.Pair;
//import org.springframework.test.web.servlet.MockMvc;
//import reactor.core.publisher.Mono;
//import shop.app.entity.CurrencyRates;
//import shop.app.entity.CurrencyType;
//import shop.app.entity.ProductEntity;
//import shop.app.exception.UnsupportedCurrencyException;
//import shop.app.repository.ProductRepository;
//import shop.app.service.currency.CurrencyServiceImpl;
//import shop.app.service.currency.ExchangeRateCache;
//import shop.app.utils.JsonMapper;
//
//import java.math.BigDecimal;
//import java.util.Map;
//import java.util.Optional;
//import java.util.UUID;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@AutoConfigureMockMvc
//@SpringBootTest(classes = ProductServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class CurrencyIntegrationTest extends AbstractIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private ProductRepository productRepository;
//    @MockBean
//    private ExchangeRateCache exchangeRateCache;
//    @MockBean
//    private CurrencyServiceImpl currencyService;
//    @SpyBean
//    private JsonMapper jsonMapper;
//
//    @Test
//    public void testCurrencyRateFromCache() throws Exception {
//        UUID productId = UUID.randomUUID();
//        CurrencyRates currencyRates = new CurrencyRates(Map.of(CurrencyType.USD.name(), new BigDecimal("1.2")));
//        ProductEntity productEntity = ProductEntity.builder()
//                .uuid(productId)
//                .price(new BigDecimal("100.00"))
//                .build();
//
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
//        Mockito.when(exchangeRateCache.getCurrencyRates()).thenReturn(Mono.just(currencyRates));
//        Mockito.when(currencyService.getExchangeRate(productEntity.getPrice()))
//                .thenReturn(Pair.of(CurrencyType.USD.name(), new BigDecimal("1.2")));
//
//        mockMvc.perform(get("http://localhost/api/v1/product/" + productId)
//                        .header("currency", "USD"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.currencyType").value(CurrencyType.USD.name()))
//                .andExpect(jsonPath("$.price").value("1.2"));
//    }
//
//    @Test
//    public void testCurrencyRateFromFile() throws Exception {
//        UUID productId = UUID.randomUUID();
//        ProductEntity productEntity = ProductEntity.builder()
//                .uuid(productId)
//                .price(new BigDecimal("100.00"))
//                .build();
//
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
//        Mockito.when(exchangeRateCache.getCurrencyRates()).thenReturn(Mono.empty());
//        Mockito.when(currencyService.getExchangeRate(productEntity.getPrice()))
//                .thenReturn(Pair.of(CurrencyType.USD.name(), new BigDecimal("1.2")));
//
//        mockMvc.perform(get("http://localhost/api/v1/product/" + productId)
//                        .header("currency", "USD"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.currencyType").value(CurrencyType.USD.name()))
//                .andExpect(jsonPath("$.price").value("1.2"));
//    }
//
//    @Test
//    public void testDefaultCurrency() throws Exception {
//        UUID productId = UUID.randomUUID();
//        ProductEntity productEntity = ProductEntity.builder()
//                .uuid(productId)
//                .price(new BigDecimal("100.0"))
//                .build();
//
//        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
//        Mockito.when(exchangeRateCache.getCurrencyRates()).thenReturn(Mono.empty());
//        Mockito.when(currencyService.getExchangeRate(productEntity.getPrice()))
//                .thenReturn(Pair.of(CurrencyType.RUB.name(), new BigDecimal("100.0")));
//        mockMvc.perform(get("http://localhost/api/v1/product/" + productId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.currencyType").value(CurrencyType.RUB.name()))
//                .andExpect(jsonPath("$.price").value("100.0"));
//    }
//
//    @Disabled
//    public void testUnsupportedCurrency_is5xxServerError() throws Exception {
//        Mockito.when(currencyService.getExchangeRate(Mockito.any()))
//                .thenThrow(new UnsupportedCurrencyException("Unsupported currency: XZY"));
//
//        mockMvc.perform(get("http://localhost/api/v1/product/" + UUID.randomUUID())
//                        .header("currency", "XZY"))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Unsupported currency: XZY"));
//    }
//}
