package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.item.enums.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemPostDto {
    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    @Schema(example = "COMPUTER",allowableValues = {"COMPUTER", "MONITOR", "MOUSE", "HEADSET", "CHAIR", "DESK"},description = "상품 카테고리")
    private Category category;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private String name;
    @NotNull
    @Schema(description = "옵션이 없는 상품 수량")
    private int defaultCount;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    private String description;
    @NotNull
    private Integer itemPrice;
    @NotNull
    private Integer deliveryPrice;
    @Schema(description = "같이 저장될 상품 옵션들 정보")
    private List<OptionPostRequestDto> optionList;
    @Schema(description = "같이 저장될 상품 스펙 정보")
    private List<ItemPostSpecDto> specList;
    private List<ImageSortDto> infoList;

    public int getTotalCount() {
        if(!this.optionList.isEmpty()){
            int optionTotalCount = this.optionList.stream().mapToInt(OptionPostRequestDto::getItemCount).sum();
            return this.defaultCount + optionTotalCount;
        }
        return this.defaultCount;
    }
}