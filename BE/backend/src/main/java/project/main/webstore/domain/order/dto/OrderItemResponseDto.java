package project.main.webstore.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.orderHistory.controller.OrderedItem;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemResponseDto { // 주문할 아이템 정보 결과
    private Long itemId;
    private Long optionId;

    private String optionContent;
    private String itemName;
    private int itemCount;
    private int itemPrice;
    private int discountPrice;
    private int discountRate;

    @Builder
    public OrderItemResponseDto(OrderedItem item) {
        this.itemId = item.getOption().getItem().getItemId();
        this.itemName = item.getOption().getItem().getItemName();
        this.itemCount = item.getCount();
        this.itemPrice = item.getPrice();
        this.discountRate = item.getOption().getItem().getDiscountRate();
        this.discountPrice = item.getDiscountedPrice();
    }
}
