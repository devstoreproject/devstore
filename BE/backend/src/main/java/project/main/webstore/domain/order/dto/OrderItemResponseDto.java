package project.main.webstore.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.main.webstore.domain.order.entity.OrderItem;

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

//    public OrderItemResponseDto(OrderItem orderItem) {
//            this.itemId = orderItem.getItem().getItemId();
//            this.itemName = orderItem.getItem().getItemName();
//            this.itemCount = orderItem.getItemCount();
//            this.itemPrice = orderItem.getItem().getItemPrice().getValue();
//        }
}
