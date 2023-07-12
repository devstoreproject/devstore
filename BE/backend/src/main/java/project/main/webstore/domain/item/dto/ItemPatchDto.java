package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
public class ItemPatchDto {
    @NotBlank
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private Category category;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private String itemName;
    @NotNull
    private int itemCount;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    private String description;
    @NotNull
    private Integer itemPrice;
    @NotNull
    private Integer deliveryPrice;
    private List<Long> deleteImageId;
    private List<ImageSortDto> imageSortAndRepresentativeInfo;

    @Builder
    public ItemPatchDto(Item item) {
        this.category = item.getCategory();
        this.itemName = item.getItemName();
        this.itemCount = item.getItemCount();
        this.description = item.getDescription();
        this.itemPrice = item.getItemPrice().getValue();
        this.deliveryPrice = item.getDeliveryPrice().getValue();
    }
}
