package project.main.webstore.domain.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrdersStatus {
    ORDER_COMPLETE("주문 완료"),
    DEPOSIT_COMPLETE("입금 완료"),

    ORDER_CANCEL("주문 취소");

    public String ordersStauts;
}

