package project.main.webstore.totalTest;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import project.main.webstore.domain.cart.dto.CartItemDto;
import project.main.webstore.domain.cart.dto.CartPostRequestDto;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class CartControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    Gson gson;
    @Autowired
    JwtTokenizer jwtTokenizer;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("카트 추가")
    void post_cart_test(){
        //토큰 발급
        UserInfoDto userInfo = new UserInfoDto("2", "admin@gmailcom", "김복자", UserRole.ADMIN);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);
        //DTO 생성
        CartPostRequestDto post = new CartPostRequestDto(List.of(new CartItemDto(1L, 20), new CartItemDto(2L, 20), new CartItemDto(3L, 20)));
        //URL 설정
        String url = "http://localhost:" + port + "/api/cart/users/2";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<CartPostRequestDto> requestEntity = new HttpEntity<>(post, headers);

        ResponseEntity<ResponseDto> responseEntity = this.template.postForEntity(url,
                requestEntity,
                ResponseDto.class);
        responseEntity.getBody();
    }
}
