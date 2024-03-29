package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.image.dto.ImageSortPostDto;
import project.main.webstore.domain.item.enums.Category;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
public class ItemPostDto {
    @Schema(example = "COMPUTER", allowableValues = {"COMPUTER", "MONITOR", "MOUSE", "HEADSET", "CHAIR", "DESK"}, description = "상품 카테고리")
    private Category category;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    @Schema(description = "상품 이름", example = "맥북")
    private String name;
    private int discountRate;
    @NotNull
    @Schema(description = "상품 설명", defaultValue = "상품에 대한 상세 설명")
    private String description;
    @NotNull
    @Schema(description = "기본이 되는 상품 가격", example = "10000000")
    private Integer itemPrice;
    @NotNull
    @Schema(description = "기본이 되는 상품 수량", example = "10")
    private Integer defaultCount;
    @NotNull
    @Schema(description = "배달비", example = "3000")
    private Integer deliveryPrice;
    @Schema(description = "같이 저장될 상품 옵션들 정보")
    private List<OptionPostRequestDto> optionList;
    @Schema(description = "저장될 이미지 정보")
    private List<ImageSortPostDto> infoList;

    public ItemPostDto(Category category, String name, int discountRate, String description, Integer itemPrice, Integer defaultCount, Integer deliveryPrice, List<OptionPostRequestDto> optionList, List<ImageSortPostDto> infoList) {
        this.category = category;
        this.name = name;
        this.discountRate = discountRate;
        this.description = description;
        this.itemPrice = itemPrice;
        this.defaultCount = defaultCount;
        this.deliveryPrice = deliveryPrice;
        this.optionList = optionList;
        this.infoList = infoList;
    }

    public ItemPostDto(Category category, String name, int discountRate, String description, Integer itemPrice, Integer defaultCount, Integer deliveryPrice, List<OptionPostRequestDto> optionList) {
        this.category = category;
        this.name = name;
        this.discountRate = discountRate;
        this.description = description;
        this.itemPrice = itemPrice;
        this.defaultCount = defaultCount;
        this.deliveryPrice = deliveryPrice;
        this.optionList = optionList;
    }
}
