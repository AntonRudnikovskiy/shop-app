package shop.app.service.image;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.app.entity.ProductEntity;
import shop.app.entity.ProductImageEntity;
import shop.app.exception.ProductNotFoundException;
import shop.app.repository.ProductImageRepository;
import shop.app.repository.ProductRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductImageService {
    @Value("${minio.host}")
    private String host;
    @Value("${minio.bucket}")
    private String bucket;
    private final MinioClient minioClient;
    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    @Transactional
    public String uploadProductImage(UUID productUUID, MultipartFile image) {
        saveImage(image);
        ProductEntity product = productRepository.findById(productUUID)
                .orElseThrow(ProductNotFoundException::new);

        String url = host + "/" + bucket + "/" + image.getName();
        ProductImageEntity productImage = ProductImageEntity.builder()
                .name(image.getName())
                .product(product)
                .imageUrl(url)
                .build();

        productImageRepository.save(productImage);
        return url;
    }

    public void downloadProductImage(UUID productUUID, HttpServletResponse response) {
        ProductEntity product = productRepository.findById(productUUID)
                .orElseThrow(ProductNotFoundException::new);

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=product-" + productUUID + "-files.zip");

        List<ProductImageEntity> productImages = product.getProductImages();
        try (ZipOutputStream zipOut = new ZipOutputStream(response.getOutputStream())) {
            for (ProductImageEntity productImage : productImages) {
                addFileToZip(productImage.getName(), zipOut);
            }
        } catch (IOException ex) {
            log.error("Error downloading file, {}", ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private void saveImage(MultipartFile image) {
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(image.getName())
                    .stream(image.getInputStream(), image.getSize(), -1)
                    .contentType(image.getContentType())
                    .build());
        } catch (Exception ex) {
            log.error("Error uploading file, {}", ex.getMessage());
            throw new RuntimeException("Error uploading file, {}", ex);
        }
    }

    private void addFileToZip(String imageName, ZipOutputStream zipOut) {
        try (InputStream fileStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(imageName)
                .build())) {
            zipOut.putNextEntry(new ZipEntry(imageName));
            fileStream.transferTo(zipOut);
            zipOut.closeEntry();
        } catch (Exception ex) {
            log.error("Error uploading file, {}", ex.getMessage());
            throw new RuntimeException("Error uploading file, {}", ex);
        }
    }
}
