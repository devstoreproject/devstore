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
    IMAGE_ALREADY_HAS(HttpStatus.BAD_REQUEST,"이미지가 이미 존재합니다."),
    IMAGE_INFO_COUNT_MISMATCH(HttpStatus.BAD_REQUEST,"이미지와 정보의 수가 일치하지 않습니다."),
    IMAGE_HAS_ALWAYS_REPRESENTATIVE(HttpStatus.BAD_REQUEST,"대표 이미지가 한개 있어야합니다."),
    IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND,"이미지를 찾을 수 없습니다."),
    IMAGE_ORDER_ALWAYS_UNIQUE(HttpStatus.BAD_REQUEST,"이미지 순서는 중복될 수 없습니다."),
    NOT_HAVE_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED,"리프레시토큰이 없습니다."),
    COVERT_ERROR(HttpStatus.BAD_REQUEST, "변환 실패 요청을 확인하시오"), PROVIDER_NOT_FOUND(HttpStatus.BAD_REQUEST,"알 수없는 소셜로그인입니다." ),
    IMAGE_NOT_POST(HttpStatus.BAD_REQUEST, "이미지 정보만 존재하고 이미지 파일이 존재하지 않습니다."),
    IMAGE_INFO_NOT_POST(HttpStatus.BAD_REQUEST, "이미지만 존재하고 이미지 정보가 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
