package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDummyResponse {
    private Long orderId;
    private String orderNumber;
    private String recipient;
    private String email;
    private String mobileNumber;
    private String homeNumber; // 선택사항
    private String zipCode;
    private String addressSimple;
    private String addressDetail;
    private String message; // 선택사항

//    private int itemCount;
//    private String itemName;
//    private int itemPrice;

    private List<OrderItemPostDto> orderItems; // 주문 아이템 리스트

    public OrderDummyResponse() {
        this.orderItems = new ArrayList<>();
    }
//    private int totalPrice;
}
