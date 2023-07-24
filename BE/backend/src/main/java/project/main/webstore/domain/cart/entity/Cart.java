package project.main.webstore.domain.cart.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    //양방향 연관계
    @OneToMany(mappedBy = "cart",orphanRemoval = true,cascade = CascadeType.ALL)
    @Setter
    private List<CartItem> cartItemList = new ArrayList<>();

    @OneToOne(mappedBy = "cart")
    private User user;

    public Cart(List<CartItem> cartItemList,User user) {
        this.cartItemList = cartItemList;
        this.user = user;
    }

    public Cart(User user) {
        this.cartItemList = new ArrayList<>();
        this.user = user;
    }

    public int getOriginalTotalPrice() {
        if(cartItemList.isEmpty()){
            throw new BusinessLogicException(CommonExceptionCode.ITEM_NOT_FOUND);
        }
        int cartPrice = cartItemList.stream().mapToInt(CartItem::getTotalPrice).sum();
        return cartPrice;
    }

    public int getDiscountedTotalPrice() {
        if(cartItemList.isEmpty()){
            throw new BusinessLogicException(CommonExceptionCode.ITEM_NOT_FOUND);
        }
        int cartPrice = cartItemList.stream().mapToInt(CartItem::getDiscountedPrice).sum();
        return cartPrice;
    }
    public Integer getDeliveryPrice(){
        return cartItemList.stream().mapToInt(cartItem -> cartItem.getOption().getItem().getDeliveryPrice().getValue()).max().orElse(0);
    }

}
