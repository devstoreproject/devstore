package project.main.webstore.domain.item.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class ItemPostSpecDto {
    @NotNull
    private String itemName;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Size(min = 0, max = 100)
    private String content;


    public ItemPostSpecDto(String itemName, String content) {
        this.itemName = itemName;
        this.content = content;
    }
}
