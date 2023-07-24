package project.main.webstore.domain.cart.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CartPostRequestDto {
    private List<CartItemDto> itemList = new ArrayList<>();

}
