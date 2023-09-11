package project.main.webstore.domain.cart.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public enum CartExceptionCode implements ExceptionCode {
    Cart_NOT_FOUND(HttpStatus.NOT_FOUND,"장바구니가 존재하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
