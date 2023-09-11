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
    @Schema(description = "질문 본문",example = "질문 본문입니다.")
    private String comment;

}
