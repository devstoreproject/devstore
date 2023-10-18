package project.main.webstore.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.image.dto.ImageDto;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

}
