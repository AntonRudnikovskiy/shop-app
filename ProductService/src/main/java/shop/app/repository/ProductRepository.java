package shop.app.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shop.app.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("SELECT p FROM ProductEntity p")
    List<ProductEntity> findAllProducts();

    @Query(nativeQuery = true, value = """
            SELECT EXISTS(SELECT article FROM products WHERE article = :article) 
            """)
    boolean existByArticle(String article);
}
