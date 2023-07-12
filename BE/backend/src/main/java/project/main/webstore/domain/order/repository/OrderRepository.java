package project.main.webstore.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.users.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findByUser(User user);
//    List<Orders> findByUserAndOrderItems_Item_IdAndOrdersStatus(User user, Long itemId, OrdersStatus ordersStatus);

    Optional<Orders> findByOrderId(Long orderId);
}
