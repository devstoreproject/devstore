package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SpecPatchDto {
    @NotNull
    private String itemName;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    private String content;

    @Builder
    public SpecPatchDto(String itemName, String content) {
        this.itemName = itemName;
        this.content = content;
    }
}