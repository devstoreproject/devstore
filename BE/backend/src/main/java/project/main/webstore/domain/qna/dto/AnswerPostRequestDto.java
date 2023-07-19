package project.main.webstore.domain.qna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "질문 등록")
public class AnswerPostRequestDto {
    @Schema(description = "질문 본문",example = "답변이 등록되는 부분입니다.")
    private String comment;
    @Schema(description = "비밀글 여부")
    private boolean isSecret;
    @Schema(description = "사용자 식별자",example = "2")
    private Long userId;

}
