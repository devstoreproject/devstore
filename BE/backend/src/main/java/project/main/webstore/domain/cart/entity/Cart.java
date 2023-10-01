package project.main.webstore.domain.cart.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.item.exception.ItemExceptionCode;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.exception.BusinessLogicException;


@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    //양방향 연관계
    @OneToMany(mappedBy = "cart",orphanRemoval = true,cascade = CascadeType.ALL)
    @Setter
    @Default
    private List<CartItem> cartItemList = new ArrayList<>();

    @Setter
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
            throw new BusinessLogicException(ItemExceptionCode.ITEM_NOT_FOUND);
        }
        int cartPrice = cartItemList.stream().mapToInt(CartItem::getTotalPrice).sum();
        return cartPrice;
    }

    public int getDiscountedTotalPrice() {
        if(cartItemList.isEmpty()){
            throw new BusinessLogicException(ItemExceptionCode.ITEM_NOT_FOUND);
        }
        int cartPrice = cartItemList.stream().mapToInt(CartItem::getDiscountedPrice).sum();
        return cartPrice;
    }
    public Integer getDeliveryPrice(){
        return cartItemList.stream().mapToInt(cartItem -> cartItem.getOption().getItem().getDeliveryPrice()).max().orElse(0);
    }
}
