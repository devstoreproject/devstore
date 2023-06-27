package project.main.webstore.domain.order.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.enums.OrdersStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderFormResponseDto { // 주문 폼 작성 ResponseDto
    private Long orderId;

//    private List<OrderItemResponseDto> orderItems;


    private String orderNumber;
    private String recipient;
    private String email;
    private String mobileNumber;
    private String homeNumber; // 선택사항
    private String zipCode;
    private String addressSimple;
    private String addressDetail;
    private String message; // 선택사항

//    private int count;
//    private int price;
//    private int discountPrice;
//    private int deliveryPrice;
//    private int totalPrice;
//    private int price;
//    private int mileage;

//    private OrdersStatus  ordersStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
