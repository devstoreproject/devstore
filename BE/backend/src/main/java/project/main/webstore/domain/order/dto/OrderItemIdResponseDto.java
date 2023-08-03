package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderItemIdResponseDto {
    private Long orderId;
    private Long itemId;
    private Long orderItemId;
}
