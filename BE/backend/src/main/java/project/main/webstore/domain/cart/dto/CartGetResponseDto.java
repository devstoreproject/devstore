package project.main.webstore.domain.cart.dto;

import lombok.Getter;
import project.main.webstore.domain.cart.entity.Cart;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CartGetResponseDto {
    private Long cartId;
    private Long userId;
    private int deliveryPrice;
    private int totalPrice;
    private int discountedPrice;
    private List<OptionDto> itemList;

    public CartGetResponseDto(Cart cart) {
        this.cartId = cart.getId();
        this.userId = cart.getUser().getId();
        this.itemList = cart.getCartItemList().stream().map(OptionDto::new).collect(Collectors.toList());
        this.deliveryPrice = cart.getDeliveryPrice();
        this.totalPrice = cart.getOriginalTotalPrice();
        this.discountedPrice = cart.getDiscountedTotalPrice();
    }
}

