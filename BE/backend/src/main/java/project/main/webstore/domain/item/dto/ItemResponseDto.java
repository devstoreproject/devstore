package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.enums.Category;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ItemResponseDto {
    private Long itemId;
    private Category category;
    private String itemName;
    private String description;

    private int itemPrice;
    private int deliveryPrice;
    private int itemCount;
    private List<SpecResponseDto> specList;
    private List<OptionResponseDto> optionList;

    @Builder
    public ItemResponseDto(Long itemId, Category category, String itemName, String description, int itemPrice,
                           int deliveryPrice, int itemCount, List<SpecResponseDto> specList, List<OptionResponseDto> optionList) {
        this.itemId = itemId;
        this.category = category;
        this.itemName = itemName;
        this.description = description;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.itemCount = itemCount;
        this.specList = specList;
        this.optionList = optionList;
    }
}