package project.main.webstore.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.users.entity.User;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
//    @Query("SELECT u FROM Users  u WHERE u.id = :id")
//    List<Orders> findByUserId(@Param("userId") Long userId);
    Optional<Orders> findByOrderId(Long orderId);
    Optional<Orders> findByOrderNumber(String orderNumber);
    @Query("SELECT o FROM Orders o WHERE o.orderId = :orderId")
    List<Orders> findAllByOrderId(@Param("orderId") Long orderId);

//    List<Orders> findByUserAndOrderItems_Item_IdAndOrdersStatus(User user, Long itemId, OrdersStatus ordersStatus);

}

