package project.main.webstore.domain.qna.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public enum QnaExceptionCode implements ExceptionCode {
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND,"질문글을 찾을 수 없습니다."),
    USER_NOT_SAME(HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}

