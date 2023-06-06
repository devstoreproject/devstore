package project.main.webstore.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseDto<T> {
    private T data;
    private String code;
    private String message;

    @Builder
    protected ResponseDto(T data, CustomCode customCode) {
        this.data = data;
        this.code = customCode.getCode();
        this.message = customCode.getMessage();
    }
}
