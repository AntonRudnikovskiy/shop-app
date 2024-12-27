package shop.app.repository.projection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductsView {
    private UUID productUUID;
    private String name;
    private Long quantity;
    private BigDecimal price;
}
