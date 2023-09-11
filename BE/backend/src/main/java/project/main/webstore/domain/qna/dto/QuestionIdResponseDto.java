package project.main.webstore.domain.qna.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionIdResponseDto {
    private Long questionId;

    public QuestionIdResponseDto(Long questionId) {
        this.questionId = questionId;
    }
}
