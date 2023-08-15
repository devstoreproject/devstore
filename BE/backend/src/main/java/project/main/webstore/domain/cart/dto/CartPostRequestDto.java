package project.main.webstore.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartPostRequestDto {
    private List<CartItemDto> itemList = new ArrayList<>();

}
