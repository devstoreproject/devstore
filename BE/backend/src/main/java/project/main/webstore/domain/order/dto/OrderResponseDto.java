package project.main.webstore.domain.order.dto;

import lombok.*;
import org.checkerframework.common.value.qual.ArrayLen;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.orderHistory.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    // OrderOrderForm
    private String orderNumber;
    private String recipient;
    private String email;
    private String mobileNumber;
    private String homeNumber;
    private String zipCode;
    private String addressSimple;
    private String addressDetail;
    private String message;


    private Long orderId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
//    private List<OrderFormResponseDto> orderFormList;
    private List<OrderItemResponseDto> orderItemList;
    private int totalPrice;
    private OrderStatus ordersStatus;

    public void OrderResponseDto(Item item) {
        this.totalPrice = item.getTotalPrice();
    }

    public OrderResponseDto(OrderStatus ordersStatus) {
        this.ordersStatus = ordersStatus;
    }
}
