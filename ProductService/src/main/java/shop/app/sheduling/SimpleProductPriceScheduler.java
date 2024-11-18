package shop.app.sheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.app.config.SimpleSchedulerCondition;
import shop.app.entity.ProductEntity;
import shop.app.repository.ProductRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
@Conditional(SimpleSchedulerCondition.class)
public class SimpleProductPriceScheduler {
    @Value("${app.scheduling.priceIncreasePercentage}")
    private double percentage;
    private final ProductRepository productRepository;

    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void simpleUpdater() {
        List<ProductEntity> productEntities = productRepository.findAllProducts().stream()
                .peek(p -> p.setPrice(p.getPrice() * percentage))
                .toList();
        productRepository.saveAll(productEntities);
    }
}
