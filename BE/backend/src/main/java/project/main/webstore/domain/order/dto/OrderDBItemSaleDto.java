package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDBItemSaleDto {
    private Long itemId;
    private String itemName;
    private Long itemPrice;
    private Long discountedPrice;

}
