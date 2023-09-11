package project.main.webstore.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionIdResponseDto {
    private Long itemId;
    private Long optionId;
}
