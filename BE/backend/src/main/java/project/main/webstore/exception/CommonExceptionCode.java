package project.main.webstore.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonExceptionCode implements ExceptionCode {
    INTERNAL_SERVER(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류"),
    ACCEPT_TYPE_ERROR(HttpStatus.NOT_ACCEPTABLE,"Accept 타입 오류"),
    NOT_HAVE_BODY(HttpStatus.NOT_ACCEPTABLE,"RequestBody가 존재하지 않습니다."),
    PARAMETER_ERROR(HttpStatus.NOT_ACCEPTABLE,"Parameter가 존재하지 않습니다."),
    USER_EMAIL_EXIST(HttpStatus.NOT_ACCEPTABLE, "Email이 이미 존재합니다."),
    USERS_NOT_VALID(HttpStatus.NOT_ACCEPTABLE, "사용자가 존재하지 않습니다."),
    DELETED_USER(HttpStatus.NOT_ACCEPTABLE, "삭제된 유저입니다."),
    EMAIL_TOKEN_EXPIRED(HttpStatus.NOT_ACCEPTABLE, "만료된 메일 토큰입니다..")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
