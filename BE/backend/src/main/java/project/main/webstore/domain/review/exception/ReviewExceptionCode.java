package project.main.webstore.domain.review.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ReviewExceptionCode implements ExceptionCode {
    REVIEW_NOT_FOUND(NOT_FOUND,"리뷰를 찾을 수 없습니다."),
    ;
    private HttpStatus httpStatus;
    private String message;
}
