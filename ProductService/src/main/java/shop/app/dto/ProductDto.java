package shop.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.app.entity.CategoryType;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    private String article;
    private String name;
    private String description;
    private CategoryType categoryType;
    private BigDecimal price;
    private Long quantity;
}
