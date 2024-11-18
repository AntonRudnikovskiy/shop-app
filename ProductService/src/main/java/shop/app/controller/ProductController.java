package shop.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shop.app.dto.CreateProductRequest;
import shop.app.dto.ProductDto;
import shop.app.dto.ProductResponseDto;
import shop.app.dto.UpdateProductDto;
import shop.app.mapper.ProductMapper;
import shop.app.service.ProductService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductMapper productMapper;
    private final ProductService productService;

    @PostMapping("/")
    public ResponseEntity<UUID> createProduct(@RequestBody CreateProductRequest createProductDto) {
        ProductDto productDto = productMapper.toProductDto(createProductDto);
        return ResponseEntity.ok(productService.createProduct(productDto));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProductByUUID(productId));
    }

    @GetMapping("/products")
    public ResponseEntity<Page<ProductResponseDto>> findAllProducts(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable)
                .map(productMapper::toProductResponseDto));
    }

    @PutMapping("/")
    public ResponseEntity<Void> updateProduct(@RequestBody UpdateProductDto updateProductDto) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}
