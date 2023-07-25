package project.main.webstore.domain.qna.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.qna.dto.*;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;

@Component
public class QnaMapper {
    public Page<QuestionDto> toResponsePage(Page<Question> questionPage) {
        return questionPage.map(QuestionDto::new);
    }

    public QuestionDto toResponseDto(Question question) {
        return new QuestionDto(question);
    }

    public Question toEntity(QuestionPostRequestDto postDto, Long itemId) {
        return new Question(postDto.getComment(), postDto.getUserId(),itemId);
    }

    public Question toEntity(QuestionPatchDto patchDto, Long questionId, Long itemId) {
        return new Question(questionId, patchDto.getComment(), patchDto.getUserId(), itemId);
    }

    public Answer toEntity(AnswerPostRequestDto postDto, Long questionId) {
        return new Answer(postDto.getComment(), questionId, postDto.getUserId());
    }

    public AnswerDto toResponseDto(Answer answer) {
        return new AnswerDto(answer);
    }
}
