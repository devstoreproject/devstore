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
    @Schema(description = "상품 이름",example = "맥북")
    private String name;
    private int discountRate;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Schema(description = "상품 설명",defaultValue = "상품에 대한 상세 설명")
    private String description;
    @NotNull
    @Schema(description = "기본이 되는 상품 가격",example = "10000000")
    private Integer itemPrice;
    @NotNull
    @Schema(description = "배달비",example = "3000")
    private Integer deliveryPrice;
    @Schema(description = "같이 저장될 상품 옵션들 정보")
    private List<OptionPostRequestDto> optionList;
    @Schema(description = "같이 저장될 상품 스펙 정보")
    private List<ItemPostSpecDto> specList;
    @Schema(description = "저장될 이미지 정보")
    private List<ImageSortDto> infoList;

}