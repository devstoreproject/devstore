package project.main.webstore.domain.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public enum OrderExceptionCode implements ExceptionCode {
    SHIPPING_INFO_NOT_FOUND(HttpStatus.BAD_REQUEST,"주소 정보가 존재하지 않습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 주문내역이 존재하지 않습니다"),
    ORDER_ALREADY_CANCEL(HttpStatus.NOT_FOUND,"이미 취소된 주문입니다."),
    ORDER_CANCEL_FAIL(HttpStatus.NOT_FOUND,"주문 취소를 실패했습니다."),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"주문한 아이템 정보가 존재하지 않습니다."),
    SPEC_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 스펙이 존재하지 않습니다."),
    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 옵션이 존재하지 않습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
