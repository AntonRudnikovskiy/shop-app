package shop.app.mapper;

import org.springframework.stereotype.Component;
import shop.app.dto.CreateProductRequest;
import shop.app.dto.ProductDto;
import shop.app.dto.ProductResponseDto;
import shop.app.entity.ProductEntity;

@Component
public class ProductMapper {
    public ProductDto toProductDto(CreateProductRequest productRequest) {
        return ProductDto.builder()
                .article(productRequest.getArticle())
                .name(productRequest.getName())
                .categoryType(productRequest.getCategoryType())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
    }

    public ProductEntity toProductEntity(ProductDto productRequest) {
        return ProductEntity.builder()
                .article(productRequest.getArticle())
                .name(productRequest.getName())
                .categoryType(productRequest.getCategoryType())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
    }
    public ProductResponseDto toProductResponseDto(ProductEntity productRequest) {
        return ProductResponseDto.builder()
                .article(productRequest.getArticle())
                .name(productRequest.getName())
                .categoryType(productRequest.getCategoryType())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .quantity(productRequest.getQuantity())
                .build();
    }
}
