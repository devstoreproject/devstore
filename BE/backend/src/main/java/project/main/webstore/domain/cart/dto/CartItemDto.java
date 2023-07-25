package project.main.webstore.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemDto {
    private Long optionId;
    private int itemCount;
}
