package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;

import java.util.List;
import java.util.stream.Collectors;

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
    private int totalPrice;
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
    @Builder(builderMethodName = "response")
    public ItemResponseDto(Item item) {
        this.itemId = item.getItemId();
        this.category = item.getCategory();
        this.itemName = item.getItemName();
        this.description = item.getDescription();
        this.itemPrice = item.getItemPrice().getValue();
        this.deliveryPrice = item.getDeliveryPrice().getValue();
        this.itemCount = item.getItemCount();
        this.specList = item.getSpecList() != null ? item.getSpecList().stream().map(SpecResponseDto::new).collect(Collectors.toList()) : null;
        this.optionList = item.getOptionList() != null ? item.getOptionList().stream().map(OptionResponseDto::new).collect(Collectors.toList()) : null;
        this.totalPrice = item.getTotalPrice();
    }
}