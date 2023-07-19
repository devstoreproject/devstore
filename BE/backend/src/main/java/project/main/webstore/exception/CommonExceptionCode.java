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
    ITEM_EXIST(HttpStatus.CONFLICT,"중복된 아이템입니다."),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"아이템이 존재하지 않습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 주문내역이 존재하지 않습니다"),
    ORDER_ALREADY_CANCEL(HttpStatus.NOT_FOUND,"이미 취소된 주문입니다."),
    ORDER_CANCEL_FAIL(HttpStatus.NOT_FOUND,"주문 취소를 실패했습니다."),
    ORDER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"주문한 아이템 정보가 존재하지 않습니다."),
    SPEC_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 스펙이 존재하지 않습니다."),
    OPTION_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 옵션이 존재하지 않습니다."),
    SHIPPING_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 배송정보가 존재하지 않습니다."),
    COVERT_ERROR(HttpStatus.BAD_REQUEST, "변환 실패 요청을 확인하시오");

    private final HttpStatus httpStatus;
    private final String message;
}
