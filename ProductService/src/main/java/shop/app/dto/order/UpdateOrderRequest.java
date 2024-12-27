package shop.app.dto.order;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import shop.app.dto.product.UpdateProductInfo;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderRequest {
    @Size(min = 1)
    private List<UpdateProductInfo> products;
}
