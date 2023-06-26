package project.main.webstore.domain.qna.dto;

import lombok.Getter;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.enums.QnaStatus;

@Getter
public class AnswerDto {
    private Long answerId;
    private boolean isSecret;
    private QnaStatus qnaStatus;
    private String comment;
    private Long userId;
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
