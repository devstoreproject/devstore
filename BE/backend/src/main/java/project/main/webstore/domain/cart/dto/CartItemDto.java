package project.main.webstore.domain.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    @Schema(example = "1")
    private Long optionId;
    @Schema(example = "10")
    private int itemCount;
}
