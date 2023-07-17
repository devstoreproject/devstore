package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionPostRequestDto {
    @Schema(description = "옵션 본문",example = "이곳은 옵션 본문 내용입니다.")
    private String optionDetail;
    @Schema(description = "옵션 상품 수량", example = "100")
    private int itemCount;
    @Schema(description = "옵션 상품 추가 가격", example = "100")
    private int additionalPrice;

}
