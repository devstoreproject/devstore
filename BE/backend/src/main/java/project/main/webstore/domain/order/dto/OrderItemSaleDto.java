package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemSaleDto {
    private Long itemId;
    private String itemName;
    private Long itemPrice;
    private Long discountedPrice;

    public OrderItemSaleDto(OrderDBItemSaleDto dto) {
        this.itemId = dto.getItemId();
        this.itemName = dto.getItemName();
        this.itemPrice = dto.getItemPrice();
        this.discountedPrice = dto.getDiscountedPrice();
    }
}
