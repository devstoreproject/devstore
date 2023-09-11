package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class OptionPostDto {
    @Schema(description = "상품 식별자",example = "1")
    private Long itemId;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Size(min = 0, max = 100)
    @Schema(description = "옵션 본문",example = "옵션 본문입니다.")
    private String optionDetail;
    @Schema(description = "옵션 상품 수",example = "100")
    private int count;
    @Schema(description = "옵션 추가 금액",example = "1000")
    private int additionalPrice;

}
