package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionPatchDto {
    private Long optionId;
    @Schema(description = "상품 옵션 정보")
    private String optionDetail;
    private String optionName;
    @Schema(description = "옵션 수량")
    private Integer itemCount;
    private Integer additionalPrice;

}
