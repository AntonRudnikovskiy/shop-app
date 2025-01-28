package shop.app.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.app.service.image.ProductImageService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class ProductImageController {
    private final ProductImageService productImageService;

    @PostMapping("/upload/{productUUID}")
    public ResponseEntity<String> uploadProductImage(@PathVariable UUID productUUID,
                                                     @RequestParam("image") MultipartFile image) {
        String productImage = productImageService.uploadProductImage(productUUID, image);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productImage);
    }

    @GetMapping("/download/{productUUID}")
    public ResponseEntity<Void> downloadProductImageZip(@PathVariable UUID productUUID, HttpServletResponse response) {
        productImageService.downloadProductImage(productUUID, response);
        return ResponseEntity.ok().build();
    }
}
