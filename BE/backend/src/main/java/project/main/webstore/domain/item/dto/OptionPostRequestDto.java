package project.main.webstore.domain.item.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OptionPostRequestDto {
    private String optionDetail;
    private int itemCount;
    private int additionalPrice;

}
