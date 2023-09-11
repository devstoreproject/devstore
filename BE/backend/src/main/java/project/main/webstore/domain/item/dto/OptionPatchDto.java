package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OptionPatchDto {
    @Schema(description = "상품 옵션 정보")
    private String optionDetail;
    @Schema(description = "옵션 수량")
    private Integer itemCount;

}
