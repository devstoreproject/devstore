package project.main.webstore.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponseDto {
    private Long itemId;
    private String itemName;
    private int itemCount;
    private int itemPrice;

    @Builder
    public OrderItemResponseDto(Long itemId, String itemName, int itemCount, int itemPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
    }
}
