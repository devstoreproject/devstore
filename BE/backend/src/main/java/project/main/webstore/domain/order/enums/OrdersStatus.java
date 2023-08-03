package project.main.webstore.domain.order.enums;

import lombok.Getter;

@Getter
public enum OrdersStatus {
    ORDER_COMPLETE(1, "주문 완료"),
    ORDER_CANCEL(2, "주문 취소"),
    PAYMENT_PROGRESS(3, "결제 진행중"),
    PAYMENT_COMPLETE(4, "결제 완료"),
    PAYMENT_CANCEL(5, "결제 취소"),
    DELIVERY_PROGRESS(6, "배송중"),
    DELIVERY_COMPLETE(7, "배송 완료"),
    REFUND_PROGRESS(8, "환불 진행중"),
    REFUND_COMPLETE(9, "환불 완료");
    public int index;
    public String ordersStatus;

    OrdersStatus(int index, String ordersStatus) {
        this.index = index;
        this.ordersStatus = ordersStatus;
    }
}

