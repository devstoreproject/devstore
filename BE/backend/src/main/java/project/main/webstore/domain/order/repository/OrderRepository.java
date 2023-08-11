package project.main.webstore.domain.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.order.entity.Orders;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNumber(String orderNumber);
    @EntityGraph(attributePaths = {"user","coupons","orderedItemList"})
    Optional<Orders> findAllByOrderNumber(String orderNumber);
    @EntityGraph(attributePaths = {"user"})
    Optional<Orders> findUserByOrderNumber(String orderNumber);
    @EntityGraph
    Optional<Orders> findAllByOrderId(Long orderId);
    @EntityGraph(attributePaths = {"user"})
    Optional<Orders> findByOrderId(Long orderId);
    @Query("SELECT o FROM Orders o WHERE o.orderId = :orderId")
    Page<Orders> findAllByOrderId(@Param("orderId") Long orderId, Pageable pageable);

    @Query("SELECT o FROM Orders o Where o.user.id = :userId")
    Page<Orders> findAllByUserId(Pageable pageable,@Param("userId") Long userId);
}

