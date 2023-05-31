package project.main.webstore.domain.cart.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class CartItem {
    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private long id;
}
