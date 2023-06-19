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
    IMAGE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"이미지 파일의 해쉬값을 찾을 수 없습니다."),
    IMAGE_ALREADY_HAS(HttpStatus.BAD_REQUEST,"이미지가 이미 존재합니다.")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
