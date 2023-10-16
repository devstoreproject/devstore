package project.main.webstore.totalTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import project.main.webstore.domain.notice.dto.NoticeIdResponseDto;
import project.main.webstore.domain.notice.dto.NoticePatchRequestDto;
import project.main.webstore.domain.notice.dto.NoticePostRequestDto;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.stub.ImageStub;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class NoticeControllerTest {
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8");
    final String URL = "http://localhost:";
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    Gson gson;
    @Autowired
    ImageStub imageStub;
    @LocalServerPort
    private int port;
    private String DEFAULT_URL = "/api/notices";

    @Test
    @DisplayName("공지 사항 등록 테스트")
    void post_notice_test() throws Exception{
        NoticePostRequestDto post = new NoticePostRequestDto(1L, "타이틀", "컨텐츠",
                NoticeCategory.EVENT);
        String content = gson.toJson(post);
        HttpEntity<MultiValueMap<String, Object>> responseEntity = imageStub.getMultipartOneImageAndJsonDataRequest(
                "post", content, imageStub.getJWTAccessTokenAdmin());

        String url = URL + port + DEFAULT_URL;
        ResponseEntity<String> response = template.postForEntity(url, responseEntity,
                String.class);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<NoticeIdResponseDto>>() {
        }.getType();
        ResponseDto<NoticeIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseDto.getData().getNoticeId()).isEqualTo(3L);
    }
    @Test
    @DisplayName("공지 사항 등록 테스트 : 사진 없음")
    void post_notice_no_image_test() throws Exception{
        NoticePostRequestDto post = new NoticePostRequestDto(1L, "타이틀", "컨텐츠",
                NoticeCategory.EVENT);
        String content = gson.toJson(post);
        HttpEntity<MultiValueMap<String, Object>> responseEntity = imageStub.getMultipartJsonDataRequest(
                "post", content, imageStub.getJWTAccessTokenAdmin());

        String url = URL + port + DEFAULT_URL;
        ResponseEntity<String> response = template.postForEntity(url, responseEntity,
                String.class);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<NoticeIdResponseDto>>() {
        }.getType();
        ResponseDto<NoticeIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(responseDto.getData().getNoticeId()).isEqualTo(3L);
    }

    @Test
    @DisplayName("공지 사항 수정 테스트 : 사진 없음")
    void patch_notice_no_image_test() throws Exception{
        NoticePatchRequestDto patch = new NoticePatchRequestDto(1L, "수정 타이틀", "수정 컨텐츠",
                NoticeCategory.EVENT);
        String content = gson.toJson(patch);
        HttpEntity<MultiValueMap<String, Object>> responseEntity = imageStub.getMultipartJsonDataRequest(
                "patch", content, imageStub.getJWTAccessTokenAdmin());

        String url = URL + port + DEFAULT_URL + "/{noticeId}";
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PATCH, responseEntity,
                String.class,1L);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<NoticeIdResponseDto>>() {
        }.getType();
        ResponseDto<NoticeIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getNoticeId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("공지 사항 수정 테스트 : 사진")
    void patch_notice_only_image_test() throws Exception{
        NoticePostRequestDto post = new NoticePostRequestDto(1L, "타이틀", "컨텐츠",
                NoticeCategory.EVENT);
        String content = gson.toJson(post);
        HttpEntity<MultiValueMap<String, Object>> responseEntity = imageStub.getMultipartJsonDataRequest(
                "post", content, imageStub.getJWTAccessTokenAdmin());

        String url = URL + port + DEFAULT_URL + "/{noticeId}";
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PATCH, responseEntity,
                String.class,1L);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<NoticeIdResponseDto>>() {
        }.getType();
        ResponseDto<NoticeIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getNoticeId()).isEqualTo(3L);
    }



}
