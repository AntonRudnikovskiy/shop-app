package shop.app.dto.product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ProductInfo {
    @NotNull
    private UUID productId;
    @NotNull
    private Long quantity;
}
