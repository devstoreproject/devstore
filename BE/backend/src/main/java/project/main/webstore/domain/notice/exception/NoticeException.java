package project.main.webstore.domain.notice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

@Getter
@RequiredArgsConstructor
public enum NoticeException implements ExceptionCode {
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND,"공지사항을 찾을 수 없습니다."),
    NOTICE_CATEGORY_NOT_FOUND(HttpStatus.BAD_REQUEST, "카테고리가 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
