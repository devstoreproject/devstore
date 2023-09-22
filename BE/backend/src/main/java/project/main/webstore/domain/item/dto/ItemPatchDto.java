package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.image.dto.ImageSortPatchDto;
import project.main.webstore.domain.item.enums.Category;

@Getter
@Setter
@Schema(description = "상품 수정 스키마")
@NoArgsConstructor
@AllArgsConstructor
public class ItemPatchDto {
    @Schema(hidden = true)
    private Long itemId;
    @Schema(example = "COMPUTER", allowableValues = {"COMPUTER", "MONITOR", "MOUSE", "HEADSET", "CHAIR", "DESK"})
    private Category category;
    @Schema(example = "맥북", description = "상품 이름")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private String name;

    @Schema(description = "상품 상세 설명", example = "수정할 내용은 맥북은 정말 멋있습니다.")
    private String description;
    @Schema(description = "옵션이 없는 상품 수량", example = "100")
    private int defaultCount;
    @Schema(description = "기본 상품 가격", example = "1000000")
    private Integer itemPrice;
    @Schema(description = "배달 가격", example = "3000")
    private Integer deliveryPrice;
    private int discountRate;
    //이미지 삭제 로직
    @Setter
    @Schema(description = "삭제할 이미지의 ID 리스트")
    private List<Long> deleteImageId;
    @Setter
    private List<Long> deleteOptionId;
    private List<OptionPatchDto> updateOptionList;
    @Setter
    @Schema(description = "이미지 정렬 및 대표사진 여부 정보 리스트")
    private List<ImageSortPatchDto> imageSortAndRepresentativeInfo;

    public ItemPatchDto(Category category, String name, String description, int defaultCount, Integer itemPrice, Integer deliveryPrice, int discountRate) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.defaultCount = defaultCount;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.discountRate = discountRate;
    }

    public ItemPatchDto(Category category, String name, String description, int defaultCount, Integer itemPrice, Integer deliveryPrice, int discountRate, List<Long> deleteImageId) {
        this.category = category;
        this.name = name;
        this.description = description;
        this.defaultCount = defaultCount;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.discountRate = discountRate;
        this.deleteImageId = deleteImageId;
    }
}
