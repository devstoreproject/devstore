package project.main.webstore.domain.qna.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;
import project.main.webstore.domain.qna.repository.AnswerRepository;
import project.main.webstore.domain.qna.repository.QuestionRepository;
import project.main.webstore.domain.qna.stub.QnaStub;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class QnaServiceTest {
    @InjectMocks
    QnaService service;
    @Mock
    QuestionRepository questionRepository;
    @Mock
    QnaValidService validService;
    @Mock
    AnswerRepository answerRepository;
    @Mock
    UserValidService userValidService;
    @Mock
    ItemService itemService;
    QnaStub qnaStub = new QnaStub();

    @Test
    @DisplayName("게시글 생성 : 성공")
    void post_question_test() throws Exception{
        // given
        Long userId = 1L;
        Long itemId = 1L;
        Long questionId = 1L;
        Question expect = qnaStub.getQuestion(questionId);
        Question questionNotId = qnaStub.getQuestion();


        given(userValidService.validUser(anyLong())).willReturn(new User(userId));
        given(itemService.validItem(anyLong())).willReturn(new Item(itemId));
        given(questionRepository.save(any(Question.class))).willReturn(expect);

        // when
        Question result = service.postQuestion(questionNotId, userId, itemId);
        // then
        Assertions.assertThat(questionNotId.getId()).isNull();
        Assertions.assertThat(result.getId()).isEqualTo(expect.getId());
        Assertions.assertThat(result.getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(result.getItem().getItemId()).isEqualTo(itemId);
        Assertions.assertThat(result.getUser()).isNotNull();
        Assertions.assertThat(result.getItem()).isNotNull();
    }

    @Test
    @DisplayName("게시글 생성 : 실패[userId 없음]")
    void post_question_failure_userId_test() throws Exception{
        // given
        Long userId = 1L;
        Long itemId = 1L;
        Question questionNotId = qnaStub.getQuestion();


        given(userValidService.validUser(anyLong())).willThrow(BusinessLogicException.class);

        // when
        Throwable throwable = catchThrowable(() -> service.postQuestion(questionNotId, userId, itemId));
        // then
        Assertions.assertThat(throwable)
                .as("사용자 Id는 항상존재해야한다.")
                .isInstanceOf(BusinessLogicException.class);
    }

    @Test
    @DisplayName("게시글 생성 : 실패[itemId 없음]")
    void post_question_failure_itemId_test() throws Exception{
        // given
        Long userId = 1L;
        Long itemId = 1L;
        Question questionNotId = qnaStub.getQuestion();

        given(userValidService.validUser(anyLong())).willReturn(new User(userId));
        given(itemService.validItem(anyLong())).willThrow(BusinessLogicException.class);

        // when
        Throwable throwable = catchThrowable(() -> service.postQuestion(questionNotId, userId, itemId));
        // then
        Assertions.assertThat(throwable)
                .as("Item Id는 항상존재해야한다.")
                .isInstanceOf(BusinessLogicException.class);
    }

    @Test
    @DisplayName("질문 수정 : 성공")
    void patch_question_test() throws Exception{
        // given
        Question question = qnaStub.getQuestion(1L);
        Question patch = qnaStub.getQuestion(2L);
        patch.addId(question.getId());
        Long userId = 1L;
        given(validService.validUserSameWithQuestion(anyLong(),anyLong())).willReturn(question);
        BDDMockito.willDoNothing().given(userValidService).validUserIdSame(anyLong(),any(Question.class));
        // when
        Question result = service.patchQuestion(patch, userId);
        // then
        assertThat(result.getId()).isEqualTo(question.getId()).isEqualTo(patch.getId());
        assertThat(result.getComment()).isEqualTo(patch.getComment());
    }




    @Test
    @DisplayName("질문글 삭제 : 성공")
    void delete_question_test() throws Exception{
        // given
        Long questionId = 1L;
        Long userId = 1L;
        given(validService.validUserSameOrAdmin(anyLong(),anyLong())).willReturn(qnaStub.getQuestion(1L));
        willDoNothing().given(questionRepository).deleteById(anyLong());
        // when
        service.deleteQuestion(questionId,userId);
        // then
    }




    @Test
    @DisplayName("답변 등록 : 성공")
    void post_answer_test() throws Exception{
        // given
        Long userId = 2L;
        Question question = qnaStub.getQuestion(1L);
        Answer answerNoId = qnaStub.getAnswer();
        Answer answer = qnaStub.getAnswer(1L);

        given(userValidService.validUser(anyLong())).willReturn(new User(userId));
        given(validService.validQuestion(anyLong())).willReturn(question);
        willDoNothing().given(validService).validAnswerExistFromQuestion(question);
        // when
        Answer result = service.postAnswer(answerNoId, userId);
        // then
        Assertions.assertThat(result.getUser().getId()).isEqualTo(userId);
        Assertions.assertThat(result.getQuestion().getId()).isEqualTo(1L);
        Assertions.assertThat(result.getQuestion().getQnaStatus()).isEqualTo(QnaStatus.ANSWER_COMPLETE);

    }

    @Test
    @DisplayName("답변 삭제 : 성공")
    void delete_answer_test() throws Exception{
        // given
        Long answerId = 1L;
        Answer answer = qnaStub.getAnswer(answerId);
        given(validService.validAnswer(anyLong())).willReturn(answer);
        // when
        service.deleteAnswer(answerId);
    }
}