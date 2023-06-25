package project.main.webstore.domain.qna.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerPostRequestDto {
    private String comment;
    private boolean isSecret;
    private Long userId;

}
