package project.main.webstore.domain.cart.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.entity.ItemOption;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;

    // 연관관계 매핑 //

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "OPTION_ID")
    private ItemOption option;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "CART_ID")
    private Cart cart;
    @Setter
    private Integer itemCount;

    public CartItem(ItemOption option, Cart cart, Integer itemCount) {
        this.option = option;
        this.cart = cart;
        this.itemCount = itemCount;
    }

    public int getDiscountedPrice() {
        double itemPrice = this.option.getItem().getItemPrice() + this.option.getAdditionalPrice();
        int itemCount = this.itemCount;
        double discountRate = this.option.getItem().getDiscountRate();

        double totalPrice = (itemPrice * itemCount) * (100 - discountRate) / 100;

        long discountPrice = Math.round(totalPrice);

        return (int) discountPrice;
    }

    public int getTotalPrice() {
        return this.itemCount * (this.option.getItem().getItemPrice() + this.option.getAdditionalPrice());
    }
}
