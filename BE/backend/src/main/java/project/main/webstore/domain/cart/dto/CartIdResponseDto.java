package project.main.webstore.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartIdResponseDto {
    private Long userId;
    private Long cartId;
}
