package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SpecPatchDto {
    @Schema(description = "스펙 이름",example = "수정한 스펙 이름입니다.")
    private String name;
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Schema(description = "스펙 본문",example = "수정한 스펙 본문입니다.")
    private String content;

    @Builder
    public SpecPatchDto(String name, String content) {
        this.name = name;
        this.content = content;
    }
}