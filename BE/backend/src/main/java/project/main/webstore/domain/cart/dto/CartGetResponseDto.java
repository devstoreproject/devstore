package project.main.webstore.domain.cart.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartGetResponseDto {

    private Long cartId;
    private Long userId;
    private int deliveryPrice;
    private int totalPrice;
    private int discountedPrice;
    private List<OptionDto> itemList;

}

