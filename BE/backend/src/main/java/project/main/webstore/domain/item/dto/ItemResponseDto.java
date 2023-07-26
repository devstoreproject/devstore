package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponseDto {
    @Schema(description = "아이템 식별자")
    private Long itemId;
    @Schema(example = "COMPUTER",allowableValues = {"COMPUTER", "MONITOR", "MOUSE", "HEADSET", "CHAIR", "DESK"})
    private Category category;
    @Schema(description = "상품 이름")
    private String name;
    @Schema(description = "상품 상세 설명")
    private String description;
    @Schema(description = "옵션이 없는 기본 상품 가격")
    private int itemPrice;
    @Schema(description = "배송비")
    private int deliveryPrice;
    @Schema(description = "전체 수량")
    private int totalCount;
    @Schema(description = "옵션이 없는 상품 전체 수량")
    private int defaultCount;
    @Schema(description = "상품의 스펙들 정보")
    private List<SpecResponseDto> specList;
    @Schema(description = "상품의 옵션들 정보")
    private List<OptionResponseDto> optionList;
    @Schema(description = "상품 사진 정보")
    private List<ImageDto> imageList;

    @Builder(builderMethodName = "response")
    public ItemResponseDto(Item item) {
        this.itemId = item.getItemId();
        this.category = item.getCategory();
        this.name = item.getItemName();
        this.description = item.getDescription();
        this.itemPrice = item.getItemPrice().getValue();
        this.deliveryPrice = item.getDeliveryPrice().getValue();
        this.defaultCount = item.getTotalCount();
        this.specList = item.getSpecList() != null ? item.getSpecList().stream().map(SpecResponseDto::new).collect(Collectors.toList()) : null;
        this.optionList = item.getOptionList() != null ? item.getOptionList().stream().map(OptionResponseDto::new).collect(Collectors.toList()) : null;
        this.totalCount = item.getTotalCount();
        this.imageList = item.getItemImageList() != null? item.getItemImageList().stream().map(ImageDto::new).collect(Collectors.toList()) : null;
    }
}