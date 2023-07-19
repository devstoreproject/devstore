package project.main.webstore.domain.qna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "질문 수정")
public class QuestionPatchDto {
    @Schema(description = "비밀글 여부",example = "false")
    private boolean isSecret;
    @Schema(description = "질문 본문",example = "질문 본문입니다.")
    private String comment;
    @Schema(description = "사용자 식별자",example = "3")
    private Long userId;

}
