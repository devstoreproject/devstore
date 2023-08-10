package project.main.webstore.domain.orderHistory.dto;

import lombok.*;
import project.main.webstore.domain.order.dto.OrderItemResponseDto;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class OrderHistoryResponseDto {
    private Long id;
    private String orderNumber;
    // ITEM
    private List<OrderItemResponseDto> orderItemList;
    private int deliveryPrice;
    private int totalPrice;
    // USER
    private String nickName;
    private String phone;
    private String email;
    // shippingInfo
    private String recipient;
    private String addressSimple;
    private String addressDetail;
    private LocalDateTime createdAt;
    @Builder
    public OrderHistoryResponseDto(OrderHistory history) {
        this.id = history.getId();
        this.orderNumber = history.getOrder().getOrderNumber();
        this.orderItemList = history.getOrder().getCart().getCartItemList().stream().map(OrderItemResponseDto::new).collect(Collectors.toList());
        this.deliveryPrice = history.getOrder().getDeliveryPrice();
        this.totalPrice = history.getOrder().getTotalPrice();
        this.nickName = history.getOrder().getUser().getNickName();
        this.phone = history.getOrder().getUser().getPhone();
        this.email = history.getOrder().getUser().getEmail();
        this.recipient = history.getOrder().getInfo().getRecipient();
        this.addressSimple = history.getOrder().getInfo().getAddress().getAddressSimple();
        this.addressDetail = history.getOrder().getInfo().getAddress().getAddressDetail();
        this.createdAt = history.getOrder().getCreatedAt();
    }
}
