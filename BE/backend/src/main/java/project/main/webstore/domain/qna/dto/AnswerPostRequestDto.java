package project.main.webstore.domain.qna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "질문 등록")
public class AnswerPostRequestDto {
    @Schema(description = "질문 본문")
    private String comment;
    @Schema(description = "비밀글 여부")
    private boolean isSecret;
    @Schema(description = "사용자 식별자")
    private Long userId;

}
