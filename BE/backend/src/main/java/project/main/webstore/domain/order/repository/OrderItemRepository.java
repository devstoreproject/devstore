package project.main.webstore.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.order.entity.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
//    List<OrderItem> findByOrderItemId(CartItem cartItem);
}
