package project.main.webstore.domain.qna.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.DefaultMapper;
import project.main.webstore.domain.qna.dto.AnswerDto;
import project.main.webstore.domain.qna.dto.AnswerPostRequestDto;
import project.main.webstore.domain.qna.dto.QuestionDto;
import project.main.webstore.domain.qna.dto.QuestionIdResponseDto;
import project.main.webstore.domain.qna.dto.QuestionPatchDto;
import project.main.webstore.domain.qna.dto.QuestionPostRequestDto;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.dto.CustomPage;

@Component
public class QnaMapper implements DefaultMapper {
    public CustomPage<QuestionDto> toResponsePage(Page<Question> questionPage) {
        Page<QuestionDto> map = questionPage.map(QuestionDto::new);
        return transCustomPage(map);
    }

    public QuestionDto toResponseDto(Question question) {
        return new QuestionDto(question);
    }

    public Question toEntity(QuestionPostRequestDto postDto) {
        return new Question(postDto.getComment());
    }

    public Question toEntity(QuestionPatchDto patchDto, Long questionId) {
        return new Question(questionId, patchDto.getComment());
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
