package project.main.webstore.domain.item.dto;

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
public class ItemPatchDto {
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private Category category;
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private String itemName;
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    private String description;
    private Integer itemPrice;
    private Integer deliveryPrice;
    private int totalCount;


    //이미지 삭제 로직
    private List<Long> deleteImageId;
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
