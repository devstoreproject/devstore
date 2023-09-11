package project.main.webstore.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.entity.ItemOption;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OptionResponseDto {
    private Long itemId;
    private Long optionId;
    private String optionDetail;
    private int additionalPrice;
    private Integer itemCount;
    private String optionName;



    public OptionResponseDto(ItemOption option) {
        this.optionId = option.getOptionId();
        this.itemId = option.getItem().getItemId();
        this.optionDetail = option.getOptionDetail();
        this.itemCount = option.getItemCount();
        this.additionalPrice = option.getAdditionalPrice();
        this.optionName = option.getOptionName();
    }
}
