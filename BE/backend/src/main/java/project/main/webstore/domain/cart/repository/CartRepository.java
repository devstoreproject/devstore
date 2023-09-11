package project.main.webstore.domain.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.main.webstore.domain.cart.entity.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {
}
