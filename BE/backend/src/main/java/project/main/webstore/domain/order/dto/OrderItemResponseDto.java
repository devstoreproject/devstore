package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto { // 주문할 아이템 정보 결과
    private Long itemId;
    private Long optionId;

    private String optionContent;
    private String itemName;
    private int itemCount;
    private int itemPrice;
    private int discountPrice;
    private int discountRate;

}
