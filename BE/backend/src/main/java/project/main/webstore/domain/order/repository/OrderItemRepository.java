package project.main.webstore.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import project.main.webstore.domain.cart.entity.CartItem;
//import project.main.webstore.domain.order.entity.OrderForm;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.order.entity.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> findByOrderItemId(Long orderItemId);
    @Query("SELECT o FROM OrderItem o WHERE o.order.orderId = :orderId")
    List<OrderItem> findAllByOrderId(@Param("orderId") Long orderId);
    @Query("SELECT o FROM OrderItem o WHERE o.item.itemId = :itemId")
    List<OrderItem> findByItemId(@Param("itemId") Long itemId);
}

//    List<OrderItem> findByOrderItemId(CartItem cartItem);

