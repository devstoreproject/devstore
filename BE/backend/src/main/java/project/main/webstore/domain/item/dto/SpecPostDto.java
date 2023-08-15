package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class SpecPostDto {
    @Schema(description = "상품 식별자",example = "1")
    private Long itemId;
    @NotNull
    @Schema(description = "스펙 이름",example = "스펙 이름")
    private String specName;
    @NotNull
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Size(min = 0, max = 100)
    @Schema(description = "스펙 이름",example = "스펙 본문")
    private String content;

    @Builder
    public SpecPostDto(Long itemId, String specName, String content) {
        this.itemId = itemId;
        this.specName = specName;
        this.content = content;
    }
}
