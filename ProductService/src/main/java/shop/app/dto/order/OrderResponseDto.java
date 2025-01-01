package shop.app.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderResponseDto {
    private UUID orderId;
    private List<OrderedProductsDto> orderedProducts;
    private BigDecimal totalPrice;
}
