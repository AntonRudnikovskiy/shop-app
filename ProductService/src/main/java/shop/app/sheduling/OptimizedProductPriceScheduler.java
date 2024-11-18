package shop.app.sheduling;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import shop.app.aspect.annotation.Loggable;
import shop.app.config.OptimizedSchedulerCondition;
import shop.app.repository.jdbc.ProductBatchPriceUpdater;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Conditional(OptimizedSchedulerCondition.class)
public class OptimizedProductPriceScheduler {
    @Value("${app.scheduling.priceIncreasePercentage}")
    private double percentage;
    private final ProductBatchPriceUpdater productBatchUpdater;

    @Loggable
    @Transactional
    @Scheduled(cron = "0 * * * * *")
    public void optimizedUpdater() {
        List<UUID> productIds = productBatchUpdater.fetchProductIds();
        productBatchUpdater.updateProductPrices(percentage, productIds);
    }
}