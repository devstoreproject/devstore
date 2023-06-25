package project.main.webstore.domain.qna.dto;

import lombok.Getter;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;

@Getter
public class QuestionDto {
    private Long questionId;
//    List<String> imagePathList = new ArrayList<>();
    private boolean isSecret;
    private String comment;
    private QnaStatus qnaStatus;
    private Long userId;
    private AnswerDto answer;

    public QuestionDto(Question question) {
        this.questionId = question.getId();
        this.isSecret = question.isSecret();
        this.comment = question.getComment();
        this.qnaStatus = question.getQnaStatus();
        this.userId = question.getUser().getId();
        this.answer = new AnswerDto(question.getAnswer());
    }
}
