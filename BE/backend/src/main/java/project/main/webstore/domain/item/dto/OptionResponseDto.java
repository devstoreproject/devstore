package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.entity.ItemOption;

@Getter
@Setter
@NoArgsConstructor
public class OptionResponseDto {
    private Long itemId;
    private Long optionId;
    private String optionDetail;

    @Builder
    public OptionResponseDto(Long itemId, Long optionId, String optionDetail) {
        this.itemId = itemId;
        this.optionId = optionId;
        this.optionDetail = optionDetail;
    }

    public OptionResponseDto(ItemOption option) {
        this.optionId = option.getOptionId();
        this.itemId = option.getItem().getItemId();
        this.optionDetail = option.getOptionDetail();
    }
}
