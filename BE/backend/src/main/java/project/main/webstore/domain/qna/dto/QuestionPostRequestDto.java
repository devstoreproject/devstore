package project.main.webstore.domain.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionPostRequestDto {
    private boolean isSecret;
    private String comment;
    private Long userId;

}
