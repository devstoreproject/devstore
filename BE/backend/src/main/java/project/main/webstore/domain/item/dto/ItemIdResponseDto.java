package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "상품 저장, 수정 응답 스키마")
public class ItemIdResponseDto {
    @Schema(description = "상품 식별자", example = "1")
    private Long itemId;
}
