package project.main.webstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "데이터 응답 틀")
@NoArgsConstructor
public class ResponseDto<T> {
    @Schema(description = "실제 데이터")
    private T data;
    @Schema(description = "HttpCode")
    private String code;
    @Schema(description = "응답 메시지")
    private String message;

    @Builder
    protected ResponseDto(T data, CustomCode customCode) {
        this.data = data;
        this.code = customCode.getCode();
        this.message = customCode.getMessage();
    }
}
