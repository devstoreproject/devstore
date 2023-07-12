package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SpecPostDto {
    private Long itemId;
    @NotNull
    private String itemName;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Size(min = 0, max = 100)
    private String content;

    @Builder
    public SpecPostDto(Long itemId, String itemName, String content) {
        this.itemId   = itemId;
        this.itemName = itemName;
        this.content     = content;
    }
}
