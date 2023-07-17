package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "COMPUTER",allowableValues = {"COMPUTER", "MONITOR", "MOUSE", "HEADSET", "CHAIR", "DESK"})
    private Category category;
    private String name;
    private String description;
    private int defaultPrice;
    private int itemPrice;
    private int deliveryPrice;
    private int totalCount;
    private int defaultCount;
    private List<SpecResponseDto> specList;
    private List<OptionResponseDto> optionList;

    @Builder
    public ItemResponseDto(Long itemId, Category category, String name, String description, int itemPrice,
                           int deliveryPrice, int defaultCount, List<SpecResponseDto> specList, List<OptionResponseDto> optionList) {
        this.itemId = itemId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.defaultCount = defaultCount;
        this.specList = specList;
        this.optionList = optionList;
    }
    @Builder(builderMethodName = "response")
    public ItemResponseDto(Item item) {
        this.itemId = item.getItemId();
        this.category = item.getCategory();
        this.name = item.getItemName();
        this.description = item.getDescription();
        this.itemPrice = item.getItemPrice().getValue();
        this.deliveryPrice = item.getDeliveryPrice().getValue();
        this.defaultPrice = item.getDefaultCount();
        this.defaultCount = item.getTotalCount();
        this.specList = item.getSpecList() != null ? item.getSpecList().stream().map(SpecResponseDto::new).collect(Collectors.toList()) : null;
        this.optionList = item.getOptionList() != null ? item.getOptionList().stream().map(OptionResponseDto::new).collect(Collectors.toList()) : null;
        this.totalCount = item.getTotalCount();
    }
}