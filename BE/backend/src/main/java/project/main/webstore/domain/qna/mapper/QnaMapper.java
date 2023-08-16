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

    public Question toEntity(QuestionPatchDto patchDto, Long questionId, Long itemId,Long userId) {
        return new Question(questionId, patchDto.getComment(), userId, itemId);
    }

    public Answer toEntity(AnswerPostRequestDto postDto, Long questionId,Long userId) {
        return new Answer(postDto.getComment(), questionId, userId);
    }

    public AnswerDto toResponseDto(Answer answer) {
        return new AnswerDto(answer);
    }

    public QuestionIdResponseDto toIdResponseDto(Question question){
        return new QuestionIdResponseDto(question.getId());
    }

    public QuestionIdResponseDto toIdResponseDto(Answer answer) {
        return new QuestionIdResponseDto(answer.getQuestion().getId());
    }
}
