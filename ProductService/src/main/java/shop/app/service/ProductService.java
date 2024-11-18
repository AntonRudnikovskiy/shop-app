package shop.app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.app.dto.ProductDto;
import shop.app.dto.ProductResponseDto;
import shop.app.entity.ProductEntity;
import shop.app.mapper.ProductMapper;
import shop.app.repository.ProductRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public UUID createProduct(ProductDto productDto) {
        String article = productDto.getArticle();
        if (!productRepository.existByArticle(article)) {
            log.error("Product with article: {} already exist", article);
            throw new RuntimeException();
        }
        ProductEntity productEntity = productMapper.toProductEntity(productDto);
        productRepository.saveAndFlush(productEntity);
        log.info("Product was successfully saved with article: {}", productEntity.getArticle());
        return productEntity.getUuid();
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductByUUID(UUID uuid) {
        ProductEntity productEntity = productRepository.findById(uuid).orElseThrow(() -> new RuntimeException());
        return productMapper.toProductResponseDto(productEntity);
    }

    @Transactional
    public ProductResponseDto updateProduct(UUID uuid) {
        return null;
    }

    public Page<ProductEntity> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Transactional
    public void deleteProduct(UUID uuid) {
        productRepository.deleteById(uuid);
    }
}