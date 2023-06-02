package project.main.webstore.domain.orderHistory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    INCART("장바구니"), BEFORE("입금 전"), PAID("결제 완료"), SHIPPING("배송 중"), COMPLETE("배송 완로"), PURCHASE("구매 확정"), CANCEL("구매 취소");

    private String description;
}
