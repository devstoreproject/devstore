package project.main.webstore.domain.qna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.enums.QnaStatus;

@Getter
@Schema(description = "공지사항 답변 데이터")
public class AnswerDto {
    @Schema(description = "공지 답변 식별자")
    private Long answerId;
    @Schema(description = "비밀글 여부")
    private boolean isSecret;
    @Schema(description = "공지사항 상태값")
    private QnaStatus qnaStatus;
    @Schema(description = "답변 본문")
    private String comment;
    @Schema(description = "사용자 식별자")
    private Long userId;
    @Schema(description = "공지 질문글 식별자")
    private Long questionId;

    public AnswerDto(Answer answer) {
        this.questionId = answer.getQuestion().getId();
        this.answerId = answer.getId();
        this.isSecret = answer.isSecret();
        this.qnaStatus = answer.getQnaStatus();
        this.comment = answer.getComment();
        this.userId = answer.getUser().getId();
    }
}
