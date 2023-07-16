package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@Schema(description = "상품 수정 스키마")
public class ItemPatchDto {
    @Schema(example = "COMPUTER",allowableValues = {"COMPUTER", "MONITOR", "MOUSE", "HEADSET", "CHAIR", "DESK"})
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private Category category;
    @Schema(example = "노트북 맥북")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private String itemName;
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    private String description;
    private Integer itemPrice;
    private Integer deliveryPrice;
    private int totalCount;


    //이미지 삭제 로직
    @Schema(implementation = Long.class, description = "삭제할 이미지의 ID 리스트")
    private List<Long> deleteImageId;
    @Schema(implementation = ImageSortDto.class,description = "이미지 정렬 및 대표사진 여부 정보 리스트")
    private List<ImageSortDto> imageSortAndRepresentativeInfo;

    @Builder
    public ItemPatchDto(Item item) {
        this.category = item.getCategory();
        this.itemName = item.getItemName();
        this.description = item.getDescription();
        this.itemPrice = item.getItemPrice().getValue();
        this.deliveryPrice = item.getDeliveryPrice().getValue();
    }
}
