package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponseDto { // 주문 폼 작성완료 정보 ResponseDto
    private String orderNumber;
    private String recipient;
    private String email;
    private String mobileNumber;
    private String homeNumber; // 선택사항
    private String zipCode;
    private String addressSimple;
    private String addressDetail;
    private String message; // 선택사항
    private int count;
    private int totalPrice;

}
