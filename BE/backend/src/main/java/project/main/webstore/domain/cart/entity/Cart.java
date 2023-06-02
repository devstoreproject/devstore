package project.main.webstore.domain.cart.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private int count;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name="value",column = @Column(name = "DELIVERY_PRICE"))
    )
    private Price deliveryPrice;

    //양방향 연관계
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItemList = new ArrayList<>();

}
