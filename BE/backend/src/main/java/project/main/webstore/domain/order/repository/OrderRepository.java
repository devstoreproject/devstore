package project.main.webstore.domain.order.repository;

import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.users.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderId(Long orderId);
    Optional<Orders> findByOrderNumber(String orderNumber);
    @Query("SELECT o FROM Orders o WHERE o.orderId = :orderId")
    Page<Orders> findAllByOrderId(@Param("orderId") Long orderId, Pageable pageable);
}

