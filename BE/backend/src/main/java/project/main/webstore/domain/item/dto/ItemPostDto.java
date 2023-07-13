package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.item.enums.Category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
public class ItemPostDto {
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
    private int totalCount;
    private List<OptionPostRequestDto> optionList;
    private List<ItemPostSpecDto> specList;
    private List<ImageSortDto> infoList;


    @Builder
    public ItemPostDto(Category category, String itemName, int itemCount, String description, Integer itemPrice, Integer deliveryPrice) {
        this.category = category;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.description = description;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
    }
}