package project.main.webstore.domain.item.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class OptionPostDto {
    private Long   itemId;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Size(min = 0, max = 100)
    private String optionDetail;

    @Builder
    public OptionPostDto(Long itemId, String optionDetail) {
        this.itemId       = itemId;
        this.optionDetail = optionDetail;
    }
}
