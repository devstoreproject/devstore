package project.main.webstore.domain.orderHistory.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;

import javax.persistence.Id;
import java.util.Optional;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    Optional<OrderHistory> findId(Long id);
    @Query("SELECT o FROM OrderHistory o WHERE o.id = :id")
    Page<Orders> findAllById(@Param("id") Long id, Pageable pageable);
}
