package project.main.webstore.domain.order.exception;

import lombok.Getter;

public enum ExceptionCode {
    ORDER_NOT_FOUND(404,"Order Not Found"),
    ORDER_ALREADY_CANCEL(404,"이미 취소된 주문입니다."),
    ORDER_CANCEL_FAIL(404,"주문 취소 실패"),
    ORDER_ITEM_NOT_FOUND(404,"order Item Not Found")

    ;

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status  = status;
        this.message = message;
    }
}
