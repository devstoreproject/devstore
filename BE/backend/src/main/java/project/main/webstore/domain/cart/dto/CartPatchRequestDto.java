package project.main.webstore.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CartPatchRequestDto {
    private List<CartItemDto> itemList = new ArrayList<>();

    public CartPatchRequestDto(List<CartItemDto> itemList) {
        this.itemList = itemList;
    }
}
