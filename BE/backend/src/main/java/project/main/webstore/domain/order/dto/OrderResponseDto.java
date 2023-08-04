package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.users.dto.ShippingAddressInfoDto;
import project.main.webstore.domain.users.dto.UserOrderDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponseDto { // 주문서 총 정보
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

    public OrderResponseDto(Orders order) {
        this.orderId = order.getOrderId();
        this.orderNumber = order.getOrderNumber();
        this.addressInfo = new ShippingAddressInfoDto(order.getInfo());
        this.userInfo = new UserOrderDto(order.getUser());
        this.orderItemList = order.getCart().getCartItemList().stream().map(OrderItemResponseDto::new).collect(Collectors.toList());
        this.totalPrice = order.getTotalPrice();
        this.discountedPrice = order.getDiscountPrice();
        this.deliveryPrice = order.getDeliveryPrice();
        this.ordersStatus = order.getOrdersStatus();
        this.message = order.getMessage();
        this.createdAt = order.getCreatedAt();
        this.modifiedAt = order.getModifiedAt();
    }
}
