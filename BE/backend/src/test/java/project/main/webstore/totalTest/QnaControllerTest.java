package project.main.webstore.totalTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import project.main.webstore.domain.qna.dto.QuestionPostRequestDto;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class QnaControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    JwtTokenizer jwtTokenizer;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("질문 등록")
    void post_question_test() throws Exception{
        // given
        UserInfoDto userInfo = new UserInfoDto("1", "admin@gmailcom", "김복자", UserRole.CLIENT);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);

        String url = "http://localhost:" + port + "/api/qna/items/{itemId}";

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of((MediaType.APPLICATION_JSON)));
        headers.setBearerAuth(accessToken);

        QuestionPostRequestDto post = new QuestionPostRequestDto("등록할 질문입니다.", 1L);
        // when
        HttpEntity<QuestionPostRequestDto> request = new HttpEntity<>(post,headers);

        ResponseEntity<ResponseDto> response = template.postForEntity(url, request, ResponseDto.class,1L);
        // then

    }
}
