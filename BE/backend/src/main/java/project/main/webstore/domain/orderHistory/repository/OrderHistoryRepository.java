package project.main.webstore.domain.orderHistory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;

import javax.persistence.Id;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
}
