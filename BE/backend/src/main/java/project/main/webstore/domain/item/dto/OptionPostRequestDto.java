package project.main.webstore.domain.item.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionPostRequestDto {
    private String optionDetail;
    private int itemCount;
}
