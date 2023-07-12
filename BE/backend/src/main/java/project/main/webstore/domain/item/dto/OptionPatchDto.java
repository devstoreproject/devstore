package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class OptionPatchDto {
    private Long optionId;
    @NotNull
    private String optionDetail;

    @Builder
    public OptionPatchDto(String optionDetail) {
        this.optionId = getOptionId();
        this.optionDetail = optionDetail;
    }
}
