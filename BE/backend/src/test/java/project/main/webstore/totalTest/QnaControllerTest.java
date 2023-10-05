package project.main.webstore.totalTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import project.main.webstore.domain.qna.dto.QuestionDto;
import project.main.webstore.domain.qna.dto.QuestionPatchDto;
import project.main.webstore.domain.qna.dto.QuestionPostRequestDto;
import project.main.webstore.domain.qna.enums.QnaStatus;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.dto.CustomPage;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.helper.TestUtils;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class QnaControllerTest {
    static final String URL = "http://localhost:";
    static final String DEFAULT_URL = "/api/qna";
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8");
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    JwtTokenizer jwtTokenizer;
    @Autowired
    TestUtils testUtils;
    @Autowired
    Gson gson;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("질문 등록")
    void post_question_test() throws Exception {
        // given
        UserInfoDto userInfo = new UserInfoDto("1", "admin@gmailcom", "김복자", UserRole.CLIENT);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);

        String url = URL + port + "/api/qna/items/{itemId}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of((MediaType.APPLICATION_JSON)));
        headers.setBearerAuth(accessToken);

        QuestionPostRequestDto post = new QuestionPostRequestDto("등록할 질문입니다.");
        // when
        HttpEntity<QuestionPostRequestDto> request = new HttpEntity<>(post, headers);

        ResponseEntity<ResponseDto> response = template.postForEntity(url, request,
                ResponseDto.class, 1L);
        // then
        ResponseDto body = response.getBody();
        LinkedHashMap data = (LinkedHashMap) body.getData();
        Assertions.assertThat(data.get("questionId")).isEqualTo(5);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("질문 수정 테스트")
    void question_patch_test() throws Exception {
        // given
        QuestionPatchDto patch = new QuestionPatchDto("이것은 수정될 Comment입니다.");
        HttpHeaders headers = testUtils.getJWTClient();

        String url = "http://localhost:" + port + "/api/qna/items/{itemId}/{questionId}";

        HttpEntity<QuestionPatchDto> request = new HttpEntity<>(patch, headers);
        // when
        ResponseEntity<ResponseDto> response = template.exchange(url, HttpMethod.PATCH, request,
                ResponseDto.class, 1L, 1L);
        // then
        System.out.println(response);
        ResponseDto body = response.getBody();
        LinkedHashMap data = (LinkedHashMap) body.getData();
        Assertions.assertThat(data.get("questionId")).isEqualTo(1);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("상품별 질문 조회 테스트")
    @Transactional
    void question_get_test() throws Exception {
        // given
        String url = "http://localhost:" + port + "/api/qna/items/{itemId}";
        // when
        ResponseEntity<String> response = template.getForEntity(url, String.class, 1L);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<CustomPage<QuestionDto>>>() {
        }.getType();
        ResponseDto<CustomPage<QuestionDto>> responseDto = gson.fromJson(body, responseType);
        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseDto.getData().getPageable().getOffset()).isZero();
        assertThat(responseDto.getData().getPageable().getSize()).isEqualTo(10);
    }

    @Test
    @DisplayName("답변 삭제")
    void answer_delete_test() throws Exception {
        // given
        Long answerId = 1L;
        Long questionId = 1L;
        String url = URL + port + DEFAULT_URL + "/{questionId}/answer/{answerId}";
        HttpHeaders jwtAdmin = testUtils.getJWTAdmin();
        HttpEntity<Object> requestEntity = new HttpEntity<>(jwtAdmin);
        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.DELETE, requestEntity,
                String.class, questionId, answerId);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @Transactional
    @DisplayName("단건 조회")
    void question_single_get_test() throws Exception {
        // given
        Long questionId = 1L;
        // when
        String url = URL + port + DEFAULT_URL + "/{questionId}";
        ResponseEntity<String> response = template.getForEntity(url, String.class, questionId);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<QuestionDto>>() {
        }.getType();
        ResponseDto<QuestionDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getQuestionId()).isEqualTo(questionId);
    }

    @Test
    @Transactional
    @DisplayName("질문 상태별 조회")
    void get_question_by_status_test() throws Exception {
        // given
        MultiValueMap param = testUtils.getPageParam();
        param.add("status", QnaStatus.ANSWER_COMPLETE.name());
        // when
        String url = URL + port + DEFAULT_URL + "/admin";
        UriComponents urlWithParam = UriComponentsBuilder.fromUriString(url).queryParams(param)
                .build();
        ResponseEntity<String> response = template.getForEntity(urlWithParam.toString(),
                String.class);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<CustomPage<QuestionDto>>>() {
        }.getType();
        ResponseDto<CustomPage<QuestionDto>> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getPageable().getSize()).isEqualTo(30);
        Assertions.assertThat(responseDto.getData().getPageable().getOffset()).isZero();
        Assertions.assertThat(
                        responseDto.getData().getContent().stream().map(QuestionDto::getQnaStatus))
                .containsOnly(QnaStatus.ANSWER_COMPLETE);
    }


    @Test
    @DisplayName("사용자별 question 조회")
    void question_user_get_test() throws Exception {
        // given
        MultiValueMap param = testUtils.getPageParam();
        HttpHeaders jwtClient = testUtils.getJWTClient();

        HttpEntity requestEntity = new HttpEntity(jwtClient);
        String url = URL + port + DEFAULT_URL + "/users";
        UriComponents urlWithParam = UriComponentsBuilder.fromUriString(url).queryParams(param)
                .build();

        ResponseEntity<String> response = template.exchange(urlWithParam.toString(), HttpMethod.GET,
                requestEntity,
                String.class);

        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<CustomPage<QuestionDto>>>() {
        }.getType();
        ResponseDto<CustomPage<QuestionDto>> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getPageable().getSize()).isEqualTo(30);
        Assertions.assertThat(responseDto.getData().getPageable().getOffset()).isZero();
        Assertions.assertThat(responseDto.getData().getContent().stream().map(
                QuestionDto::getUserId)).containsOnly(1L);
    }
}
