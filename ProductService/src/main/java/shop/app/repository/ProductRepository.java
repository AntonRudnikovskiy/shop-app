package shop.app.repository;

import feign.Param;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.app.entity.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID>, JpaSpecificationExecutor<ProductEntity> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT p FROM ProductEntity p")
    List<ProductEntity> findAllProducts();

    @Query(nativeQuery = true, value = """
            SELECT EXISTS(SELECT article FROM products WHERE article = :article) 
            """)
    boolean existByArticle(String article);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM ProductEntity p JOIN FETCH p.orderedProducts op JOIN FETCH op.order o JOIN FETCH o.customer " +
            "WHERE p.uuid = :productId AND p.quantity > 0 AND p.isAvailable = true")
    Optional<ProductEntity> findAvailableProduct(@Param("productId") UUID productId);
}
