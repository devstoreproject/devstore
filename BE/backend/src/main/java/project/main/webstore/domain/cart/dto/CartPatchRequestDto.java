package project.main.webstore.domain.cart.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartPatchRequestDto {
    private List<CartItemDto> patchItemList = new ArrayList<>();
    private List<Long> deleteOptionId = new ArrayList<>();


}
