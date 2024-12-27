package shop.app.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.app.dto.ProductInfo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponse {
    private String deliveryAddress;
    private List<ProductInfo> products;
}
