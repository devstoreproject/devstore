package project.main.webstore.domain.cart.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.image.dto.ImageDto;

@Getter
@NoArgsConstructor
public class OptionDto {
    private Long itemId;
    private Long optionId;
    private int count;
    private int defaultPrice;
    private int additionalPrice;
    private int discountRate;
    private String itemName;
    private String optionName;
    private String optionDetail;
    private ImageDto imageInfo;

    public OptionDto(CartItem entity) {
        this.itemId = entity.getOption().getItem().getItemId();
        this.itemName = entity.getOption().getItem().getItemName();
        this.optionId = entity.getOption().getOptionId();
        this.count = entity.getItemCount();
        this.defaultPrice = entity.getOption().getItem().getItemPrice();
        this.additionalPrice = entity.getOption().getAdditionalPrice();
        this.discountRate = entity.getOption().getItem().getDiscountRate();
        this.optionName = entity.getOption().getOptionName();
        this.optionDetail = entity.getOption().getOptionDetail();
        this.imageInfo =entity.getOption().getItem().getDefaultImage() != null ? new ImageDto(entity.getOption().getItem().getDefaultImage()) : null;
    }
}
