package shop.app.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import shop.app.dto.CreateProductRequest;
import shop.app.dto.ProductDto;
import shop.app.dto.ProductResponseDto;
import shop.app.entity.CurrencyType;
import shop.app.entity.ProductEntity;
import shop.app.service.сurrency.CurrencyServiceImpl;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CurrencyServiceImpl currencyService;

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
                .price(currencyService.getExchangeRate(productRequest.getPrice()).getSecond())
                .quantity(productRequest.getQuantity())
                .currencyType(CurrencyType.fromCurrency(currencyService.getExchangeRate(
                        productRequest.getPrice()).getFirst()))
                .build();
    }
}
