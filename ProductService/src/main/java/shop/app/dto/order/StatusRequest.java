package shop.app.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.app.entity.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
public class StatusRequest {
    private OrderStatus orderStatus;
}
