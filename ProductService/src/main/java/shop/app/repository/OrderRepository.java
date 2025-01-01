package shop.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.relational.core.sql.LockMode;
import org.springframework.data.relational.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.app.entity.OrderEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Lock(LockMode.PESSIMISTIC_WRITE)
    @Query("SELECT o FROM OrderEntity o JOIN FETCH o.orderedProducts op JOIN FETCH o.customer c JOIN FETCH op.product WHERE o.uuid = :orderId")
    Optional<OrderEntity> findOrderWithProducts(@Param("orderId") UUID orderId);
}
