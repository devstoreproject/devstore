package project.main.webstore.domain.qna.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.repository.AnswerRepository;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.domain.qna.stub.QnaStub;
import project.main.webstore.exception.BusinessLogicException;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QnaValidServiceTest {
    @InjectMocks
    QnaValidService validService;
    @Mock
    QuestionRepository questionRepository;
    @Mock
    AnswerRepository answerRepository;
    QnaStub qnaStub = new QnaStub();



    @Test
    @DisplayName("질문글 존재 여부 검증 : 성공")
    void valid_question_test() throws Exception{
        // given
        Long questionId = 1L;
        Question question = qnaStub.getQuestion(questionId);

        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));
        // when
        Question result = validService.validQuestion(questionId);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(question);
    }

    @Test
    @DisplayName("질문글 존재 여부 검증 : 성공")
    void valid_question_failure_test() throws Exception{
        // given
        Long questionId = 1L;

        given(questionRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        //when then
        Assertions.assertThatThrownBy(() -> validService.validQuestion(questionId)).isInstanceOf(BusinessLogicException.class).hasMessage("질문글을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("사용자와 작성자 동일인물 검증 테스트 : 성공")
    void valid_userId_same_test() throws Exception{
        // given
        Long questionId = 1L;
        Question question = qnaStub.getQuestion(questionId);
        Long userId = 1L;

        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));
        //when
        Question result = validService.validUserSameWithQuestion(userId);
        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(question);
    }

    @Test
    @DisplayName("사용자와 작성자 동일인물 검증 테스트 : 실패")
    void valid_userId_same_failure_test() throws Exception{
        // given
        Long questionId = 1L;
        Question question = qnaStub.getQuestion(questionId);
        Long userId = 2L;
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));
        //when then
        Assertions.assertThatThrownBy(() -> validService.validUserSameWithQuestion(userId)).isInstanceOf(BusinessLogicException.class).hasMessage("작성자가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("동일인 Or 관리자 여부 체크 : 성공")
    void valid_userId_same_or_admin_test() throws Exception{
        Long questionId = 1L;
        Question question = qnaStub.getQuestion(questionId);
        Long userId = 1L;
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));
        //when
        Question result = validService.validUserSameOrAdmin(userId);
        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(question);
    }

    @Test
    @DisplayName("동일인 Or 관리자 여부 체크 : 실패")
    void valid_userId_same_or_admin_failure_test() throws Exception{
        Long questionId = 1L;
        Question question = qnaStub.getQuestion(questionId);
        Long userId = 2L;
        given(questionRepository.findById(anyLong())).willReturn(Optional.of(question));
        //when
        Assertions.assertThatThrownBy(() -> validService.validUserSameOrAdmin(userId)).isInstanceOf(BusinessLogicException.class).hasMessage("작성자가 일치하지 않습니다.");
    }

    @Test
    @DisplayName("답변 검증")
    void valid_answer_test() throws Exception{
        // given
        Answer answer = qnaStub.getAnswer(1L);
        Long answerId = 1L;
        given(answerRepository.findById(anyLong())).willReturn(Optional.of(answer));
        // when
        Answer result = validService.validAnswer(answerId);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(answer);
    }

    @Test
    @DisplayName("답변 검증 : 실패")
    void valid_answer_failure_test() throws Exception{
        // given
        Answer answer = qnaStub.getAnswer(1L);
        Long answerId = 1L;
        given(answerRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        // when then
        Assertions.assertThatThrownBy(() -> validService.validAnswer(answerId)).isInstanceOf(BusinessLogicException.class).hasMessage("질문글을 찾을 수 없습니다.");
    }

}