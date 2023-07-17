package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class ItemPostSpecDto {
    @NotNull
    @Schema(description = "스펙 이름",example = "스펙1")
    private String name;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Schema(description = "스펙 본문",example = "스펙 본문 1")
    private String content;


    public ItemPostSpecDto(String name, String content) {
        this.name = name;
        this.content = content;
    }
}
