package project.main.webstore.domain.qna.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.qna.dto.AnswerPostRequestDto;
import project.main.webstore.domain.qna.dto.QuestionPatchDto;
import project.main.webstore.domain.qna.dto.QuestionPostRequestDto;
import project.main.webstore.domain.qna.entity.Answer;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;
import project.main.webstore.domain.qna.mapper.QnaMapper;
import project.main.webstore.domain.qna.service.QnaGetService;
import project.main.webstore.domain.qna.service.QnaService;
import project.main.webstore.domain.qna.stub.QnaStub;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.helper.TestUtils;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class QnaControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    QnaService service;
    @MockBean
    QnaGetService getService;
    @Autowired
    QnaMapper mapper;
    @Autowired
    Gson gson;
    TestUtils utils = new TestUtils();
    QnaStub qnaStub = new QnaStub();
    String DEFAULT_URL = "/api/qna";

    @Test
    @DisplayName("상품Qna조회 : 상품별")
    @WithMockCustomUser
    void qna_get_by_item_test() throws Exception {
        // given
        MultiValueMap pageParam = utils.getPageParam();
        Long itemId = 1L;
        Page<Question> qnaPage = qnaStub.getQnaPage(10L);
        given(getService.findQnaByItemId(any(Pageable.class), anyLong())).willReturn(qnaPage);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/items/{itemId}", itemId)
            .params(pageParam).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        // then
        perform
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content[0].questionId").value(1L));

    }

    @Test
    @DisplayName("상품Qna조회 : 사용자별")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT)
    void qna_get_by_user_test() throws Exception {
        // given
        MultiValueMap pageParam = utils.getPageParam();
        Long userId = 1L;
        Page<Question> qnaPage = qnaStub.getQnaPage(10L);
        given(getService.findQnaByUserId(any(Pageable.class), anyLong())).willReturn(qnaPage);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/users", userId).params(pageParam).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        // then
        perform
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content[0].questionId").value(1L));
    }

    @Test
    @DisplayName("상품Qna조회 : 단건조회")
    @WithMockCustomUser
    void qna_get_single_test() throws Exception {
        // given
        Long questionId = 1L;
        Question qna = qnaStub.getQuestion(questionId);
        given(getService.findQuestion(anyLong())).willReturn(qna);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/{questionId}", questionId).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        // then
        perform
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.questionId").value(qna.getId()))
            .andExpect(jsonPath("$.data.userId").value(1L));
    }

    @Test
    @DisplayName("상품Qna조회")
    @WithMockCustomUser
    void qna_get_admin_test() throws Exception {
        // given
        Page<Question> qnaPage = qnaStub.getQnaPage(10L);
        MultiValueMap pageParam = utils.getPageParam();
        given(getService.findQuestionByStatus(any(Pageable.class),any())).willReturn(qnaPage);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/admin")
            .params(pageParam)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        // then
        List<Question> content = qnaPage.getContent();
        perform
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content[0].questionId").value(content.get(0).getId()));
    }

    @Test
    @DisplayName("상품Qna조회")
    @WithMockCustomUser
    void qna_get_admin_status_test() throws Exception {
        // given
        Page<Question> qnaPage = qnaStub.getQnaPage(10L);
        MultiValueMap pageParam = utils.getPageParam();
        given(getService.findQuestionByStatus(any(Pageable.class),anyString())).willReturn(qnaPage);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/admin")
            .params(pageParam)
            .param("status", QnaStatus.REGISTER.name())
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
        // then
        List<Question> content = qnaPage.getContent();
        perform
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.content[0].questionId").value(content.get(0).getId()));
    }

    @Test
    @DisplayName("등록 테스트")
    @WithMockCustomUser(userId = 1L, userRole = UserRole.CLIENT, role = "CLIENT")
    void post_question_test() throws Exception {
        // given
        QuestionPostRequestDto post = new QuestionPostRequestDto("이것은 무엇인가요?");
        String content = gson.toJson(post);
        Long itemId = 1L;
        HttpHeaders defaultHeader = utils.getDefaultHeader();

        given(service.postQuestion(any(Question.class), anyLong(), anyLong())).willReturn(qnaStub.getQuestion(1L));

        // when
        ResultActions perform = mvc.perform(post(DEFAULT_URL + "/items/{itemId}", itemId).headers(defaultHeader).content(content));
        // then
        perform
            .andDo(log())
            .andExpect(jsonPath("$.data.questionId").value(1L))
            .andExpect(MockMvcResultMatchers.header().string("Location", "/api/qna/1"));
    }

    @Test
    @DisplayName("수정 테스트")
    @WithMockCustomUser(userId = 1L, userRole = UserRole.CLIENT, role = "CLIENT")
    void patch_question_test() throws Exception {
        // given
        QuestionPatchDto patch = new QuestionPatchDto("이것은 내용의 수정입니다.");
        String content = gson.toJson(patch);
        Long itemId = 1L;
        Long questionId = 1L;
        HttpHeaders defaultHeader = utils.getDefaultHeader();

        Question qna = qnaStub.getQuestion(1L);
        given(service.patchQuestion(any(Question.class), anyLong())).willReturn(qna);

        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch(DEFAULT_URL + "/items/{itemId}/{questionId}", itemId, questionId).headers(defaultHeader).content(content));
        // then
        perform
            .andDo(log())
            .andExpect(jsonPath("$.data.questionId").value(1L))
            .andExpect(MockMvcResultMatchers.header().string("Location", "/api/qna/1"));
    }

    @Test
    @DisplayName("질문 삭제 테스트")
    @WithMockCustomUser(userId = 1L, userRole = UserRole.CLIENT, role = "CLIENT")
    void delete_question_test() throws Exception {
        // given
        Long questionId = 1L;

        BDDMockito.willDoNothing().given(service).deleteQuestion(anyLong(), anyLong());

        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URL + "/{questionId}", questionId));
        // then
        perform
            .andDo(log())
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("답변 등록 테스트")
    @WithMockCustomUser
    void post_answer_test() throws Exception {
        // given
        AnswerPostRequestDto post = new AnswerPostRequestDto("이곳은 답변을 쓰는 칸입니다.");
        String content = gson.toJson(post);
        Long questionId = 1L;
        HttpHeaders defaultHeader = utils.getDefaultHeader();

        given(service.postAnswer(any(Answer.class), anyLong())).willReturn(qnaStub.getAnswer(1L));

        // when
        ResultActions perform = mvc.perform(post(DEFAULT_URL + "/{questionId}/answer", questionId).headers(defaultHeader).content(content));
        // then
        perform
            .andDo(log())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.questionId").value(1L))
            .andExpect(jsonPath("$.code").value("C201"))
            .andExpect(MockMvcResultMatchers.header().string("Location", "/api/qna/1"));
    }

    @Test
    @DisplayName("질문 삭제 테스트")
    @WithMockCustomUser(userId = 1L, userRole = UserRole.CLIENT, role = "CLIENT")
    void delete_answer_test() throws Exception {
        // given
        Long questionId = 1L;
        Long answerId = 1L;
        BDDMockito.willDoNothing().given(service).deleteAnswer(anyLong());

        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URL + "/{questionId}/answer/{answerId}", questionId, answerId));
        // then
        perform
            .andDo(log())
            .andExpect(status().isNoContent());
    }

}
