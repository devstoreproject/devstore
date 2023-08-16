package project.main.webstore.totalTest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.qna.dto.AnswerPostRequestDto;
import project.main.webstore.domain.qna.dto.QuestionPatchDto;
import project.main.webstore.domain.qna.dto.QuestionPostRequestDto;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.helper.TestUtils;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.util.LinkedHashMap;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class QnaControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    MockMvc mvc;
    @Autowired
    JwtTokenizer jwtTokenizer;
    @LocalServerPort
    private int port;
    @Autowired
    TestUtils testUtils;


    @Test
    @DisplayName("질문 등록")
    @Transactional
    void post_question_test() throws Exception {
        // given
        UserInfoDto userInfo = new UserInfoDto("1", "admin@gmailcom", "김복자", UserRole.CLIENT);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);

        String url = "http://localhost:" + port + "/api/qna/items/{itemId}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of((MediaType.APPLICATION_JSON)));
        headers.setBearerAuth(accessToken);

        QuestionPostRequestDto post = new QuestionPostRequestDto("등록할 질문입니다.", 1L);
        // when
        HttpEntity<QuestionPostRequestDto> request = new HttpEntity<>(post, headers);

        ResponseEntity<ResponseDto> response = template.postForEntity(url, request, ResponseDto.class, 1L);
        // then
        ResponseDto body = response.getBody();
        LinkedHashMap data = (LinkedHashMap) body.getData();
        Assertions.assertThat(data.get("questionId")).isEqualTo(1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("질문 수정 테스트")
    @Transactional
    void question_patch_test() throws Exception {
        // given
        QuestionPatchDto patch = new QuestionPatchDto("이것은 수정될 Comment입니다.");
        HttpHeaders headers = testUtils.getJWTClient();

        String url = "http://localhost:" + port + "/api/qna/items/{itemId}/{questionId}";

        HttpEntity<QuestionPatchDto> request = new HttpEntity<>(patch, headers);
        // when
        ResponseEntity<ResponseDto> response = template.exchange(url, HttpMethod.PATCH, request, ResponseDto.class, 1L, 1L);
        // then
        System.out.println(response);
        ResponseDto body = response.getBody();
        LinkedHashMap data = (LinkedHashMap) body.getData();
        Assertions.assertThat(data.get("questionId")).isEqualTo(1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    @DisplayName("답변 등록 테스트")
    @Transactional
    void answer_post_test() throws Exception {
        // given
        AnswerPostRequestDto post = new AnswerPostRequestDto("답변 등록입니다.");
        HttpHeaders headers = testUtils.getJWTAdmin();

        String url = "http://localhost:" + port + "/api/qna/{questionId}/answer";

        HttpEntity<AnswerPostRequestDto> request = new HttpEntity<>(post, headers);
        // when
        ResponseEntity<ResponseDto> response = template.postForEntity(url, request, ResponseDto.class, 1L);
        // then
        LinkedHashMap data = (LinkedHashMap) response.getBody().getData();
        assertThat(data.get("answerId")).isEqualTo(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }


    @Test
    @DisplayName("상품별 질문 조회 테스트")
    @Transactional
    void question_get_test() throws Exception {
        // given
        String url = "http://localhost:" + port + "/api/qna/items/{itemId}";
        // when
        ResponseEntity<ResponseDto> response = template.getForEntity(url, ResponseDto.class, 1L);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        mvc.perform(MockMvcRequestBuilders.post("/api/qna/items/{itemId}",1L).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data.content.questionId").value(1L));
    }


}
