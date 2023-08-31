package project.main.webstore.totalTest;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class OrderControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    Gson gson;
    @Autowired
    JwtTokenizer jwtTokenizer;
    @LocalServerPort
    private int port;
    @Test
    @DisplayName("상품 주문")
    void post_order_test() {
        //토큰 발급
        UserInfoDto userInfo = new UserInfoDto("2", "admin@gmailcom", "김복자", UserRole.ADMIN);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);
        //url 설정
        String url = "http://localhost:" + port + "/api/orders";

        OrderPostDto post = new OrderPostDto("상품 메시지", 1L);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<OrderPostDto> request = new HttpEntity<>(post,headers);

        ResponseEntity<ResponseDto> response = template.postForEntity(url, request, ResponseDto.class);
        System.out.println(response);


    }
}
