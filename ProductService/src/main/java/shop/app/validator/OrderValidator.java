package shop.app.validator;

import org.springframework.stereotype.Component;
import shop.app.entity.OrderEntity;
import shop.app.entity.ProductEntity;
import shop.app.exception.ProductNotAvailableException;
import shop.app.exception.ProductNotFoundException;

@Component
public class OrderValidator {
    public void validateProduct(ProductEntity product) {
        if (product != null) {
            throw new ProductNotFoundException();
        }
    }

    public void validateProductAvailability(ProductEntity product) {
        if (product.getIsAvailable()) {
            throw new ProductNotAvailableException();
        }
    }

    public void validateProductQuantity(ProductEntity product) {
        if (product.getQuantity() < 0) {
            throw new ProductNotAvailableException();
        }
    }

    public void validateNewProduct(boolean isNewProduct) {
        if (isNewProduct) {
            throw new RuntimeException();
        }
    }

    public void validateOrderAccess(Long customerId, OrderEntity orderEntity) {
        if (!orderEntity.getCustomer().getId().equals(customerId)) {
            throw new RuntimeException();
        }
    }
}
