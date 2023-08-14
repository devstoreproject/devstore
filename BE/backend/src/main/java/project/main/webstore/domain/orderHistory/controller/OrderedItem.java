package project.main.webstore.domain.orderHistory.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class OrderedItem extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    @Setter
    @Column(nullable = false)
    private int price;
    private int discountedPrice;
    private int discountRate;
    private int itemCount;
    private String itemDetails;
    private String optionDetails;
    @OneToOne
    private ItemOption option;
    @OneToOne
    private Item item;

    public OrderedItem(CartItem cartItem) {
        this.price =cartItem.getOption().getAdditionalPrice() + cartItem.getOption().getItem().getItemPrice();
        this.discountRate = cartItem.getDiscountedPrice();
        this.optionDetails = cartItem.getOption().getOptionDetail();
        this.itemDetails = cartItem.getOption().getItem().getDescription();
        this.option = cartItem.getOption();
        this.itemCount = cartItem.getItemCount();
        this.item = cartItem.getOption().getItem();
        this.discountedPrice = cartItem.getDiscountedPrice();
    }

}
