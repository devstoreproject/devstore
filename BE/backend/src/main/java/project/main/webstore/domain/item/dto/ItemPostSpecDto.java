package project.main.webstore.domain.item.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class ItemPostSpecDto {
    @NotNull
    private String itemName;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    private String content;


    public ItemPostSpecDto(String itemName, String content) {
        this.itemName = itemName;
        this.content = content;
    }
}
