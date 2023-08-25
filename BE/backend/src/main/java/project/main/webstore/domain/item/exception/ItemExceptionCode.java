package project.main.webstore.domain.item.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public enum ItemExceptionCode implements ExceptionCode {
    ITEM_NOT_ENOUGH(HttpStatus.BAD_REQUEST,"상품 수량 부족"),

    ITEM_NOT_SAME(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}