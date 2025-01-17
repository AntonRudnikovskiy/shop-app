package shop.app.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.app.dto.CustomerInfo;
import shop.app.entity.OrderStatus;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderInfo {
    private UUID id;
    private CustomerInfo customer;
    private OrderStatus status;
    private String deliveryAddress;
    private Long quantity;
}
