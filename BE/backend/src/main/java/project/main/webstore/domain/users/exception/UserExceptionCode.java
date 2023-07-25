package project.main.webstore.domain.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import project.main.webstore.exception.ExceptionCode;

@Getter
@AllArgsConstructor
public enum UserExceptionCode implements ExceptionCode {
    USER_EXIST(HttpStatus.CONFLICT,"이미 가입한 메일 존재"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"회원을 찾을 수 없습니다."),
    USER_JWT_EXIST(HttpStatus.CONFLICT,"JWT 회원이 이미 존재 합니다."),
    USER_NOT_JWT(HttpStatus.CONFLICT, "소셜 로그인 회원입니다."),
    USER_NOT_ACCESS(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),
    USER_INFO_MISMATCH(HttpStatus.CONFLICT, "회원 정보가 일치하지 않습니다."),
    USER_NOT_LOGIN(HttpStatus.UNAUTHORIZED, "회원이 접근할 수 없습니다."),
    NOT_ADMIN(HttpStatus.UNAUTHORIZED, "관리자가 아닙니다."),
    USER_MAIL_TIME_OUT(HttpStatus.CONFLICT,"메일 인증 기간 만료")
    ;
    private final HttpStatus httpStatus;

    private final String message;
}
