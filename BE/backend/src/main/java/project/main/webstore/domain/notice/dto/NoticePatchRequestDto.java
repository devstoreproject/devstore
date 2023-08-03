package project.main.webstore.domain.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.notice.enums.NoticeCategory;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticePatchRequestDto {
    @NotNull
    @Schema(example = "2")
    private Long userId;
    @Schema(example = "수정할 타이틀 명")
    private String title;
    @Schema(example = "수정할 본문 명")
    private String content;
    @Schema(example = "EVENT",allowableValues = {"OPERATING","UPDATE","EVENT"})
    private NoticeCategory category;
}
