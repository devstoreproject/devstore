package project.main.webstore.domain.qna.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;

@Getter
@NoArgsConstructor
public class QuestionDto {
    @Schema(description = "질문글 식별자")
    private Long questionId;
    @Schema(description = "질문 본문")
    private String comment;
    @Schema(description = "질문 상태", allowableValues = {"REGISTER","ANSWER_COMPLETE"})
    private QnaStatus qnaStatus;
    @Schema(description = "사용자 식별자")
    private Long userId;
    @Schema(description = "상품 식별자")
    private Long itemId;
    @Schema(description = "답변 정보",implementation = AnswerDto.class)
    private AnswerDto answer;

    public QuestionDto(Question question) {
        this.questionId = question.getId();
        this.comment = question.getComment();
        this.qnaStatus = question.getQnaStatus();
        this.userId = question.getUser().getId();
        this.itemId = question.getItem().getItemId();
        this.answer = question.getAnswer() != null ? new AnswerDto(question.getAnswer()) : null;
    }
}
