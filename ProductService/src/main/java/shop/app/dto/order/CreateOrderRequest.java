package shop.app.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import shop.app.dto.ProductInfo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CreateOrderRequest {
    @NotBlank
    private String deliveryAddress;
    @Size(min = 1)
    private List<ProductInfo> products;
}
