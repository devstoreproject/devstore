package project.main.webstore.domain.payment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public enum PaymentExceptionCode implements ExceptionCode {
    PAYMENT_AUTHENTIC_NOT_FOUND(HttpStatus.UNAUTHORIZED,"토큰 도입 시 권한 인증을 실패했습니다."),
    PAYMENT_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"결제 서버 문제 발생" ),
    PAYMENT_SERVER_CONNECTING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "결제 서버 연결 문제 발생"),
    FABRICATED_PAYMENT(HttpStatus.CONFLICT,"결제 정보 불일치"),
    PREPARE_GET_ERROR(HttpStatus.CONFLICT,"사전 금액 조회 실패"),
    PAYMENT_SERVER_AUTHINC_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"결제 서버 권한 문제 발생"),
    PAYMENT_AMOUNT_CONFLICT(HttpStatus.CONFLICT,"가격 불일치"),
    PAYMENT_ACCESS_TIME_ERROR(HttpStatus.CONFLICT,"결제 요청 시간 초과"),
    ;
    private HttpStatus httpStatus;
    private String message;
}
