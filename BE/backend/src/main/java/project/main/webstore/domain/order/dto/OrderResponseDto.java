package project.main.webstore.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.users.dto.ShippingAddressInfoDto;
import project.main.webstore.domain.users.dto.UserOrderDto;
import project.main.webstore.dto.Dto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto implements Dto { // 주문서 총 정보
    private Long orderId;
    private String orderNumber;

    private ShippingAddressInfoDto addressInfo;
    private UserOrderDto userInfo;

    private List<OrderItemResponseDto> orderItemList;

    private int totalPrice;
    private int discountedPrice;
    private int deliveryPrice;

    private OrdersStatus ordersStatus;
    private String message;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
