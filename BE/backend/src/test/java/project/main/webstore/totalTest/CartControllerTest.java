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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.*;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.cart.dto.*;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.helper.TestUtils;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class CartControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    Gson gson;
    @Autowired
    JwtTokenizer jwtTokenizer;
    @Autowired
    TestUtils testUtils;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("카트 추가")
    void post_cart_test(){

        //DTO 생성
        CartPostRequestDto post = new CartPostRequestDto(List.of(new CartItemDto(1L, 20), new CartItemDto(2L, 20), new CartItemDto(3L, 20)));
        //URL 설정
        String url = "http://localhost:" + port + "/api/cart/users/1";

        HttpHeaders headers = testUtils.getJWTClient();

        HttpEntity<CartPostRequestDto> requestEntity = new HttpEntity<>(post, headers);

        ResponseEntity<ResponseDto> responseEntity = this.template.postForEntity(url,
                requestEntity,
                ResponseDto.class);
        responseEntity.getBody();
    }




    @Test
    @DisplayName("장바구니 제품 수량 변경")
    void cart_patch_test() throws Exception{
        // given
        HttpHeaders headers = testUtils.getJWTClient();
        CartPatchRequestDto patch = new CartPatchRequestDto(List.of(new CartItemDto(25L, 10), new CartItemDto(26L, 10)));
        HttpEntity<CartPatchRequestDto> request = new HttpEntity<>(patch,headers);
        String url = "http://localhost:" + port + "/api/cart/users/{userId}";

        // when
        ResponseEntity<ResponseDto<CartIdResponseDto>> response = template.exchange(url, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {}, 1L);
        // then
        ResponseDto<CartIdResponseDto> body = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getData().getCartId()).isEqualTo(1L);
    }




    @Test
    @DisplayName("카트 전체 삭제")
    @Transactional
    void cart_all_delete() throws Exception{
        // given
        HttpHeaders headers = testUtils.getJWTClient();
        CartDeleteDto patch = new CartDeleteDto(List.of(1L, 2L));
        String url = "http://localhost:" + port + "/api/cart/users/{userId}/del";

        HttpEntity<CartDeleteDto> request = new HttpEntity<>(patch,headers);
        // when
        ResponseEntity<ResponseDto<CartIdResponseDto>> response = template.exchange(url, HttpMethod.PATCH, request, new ParameterizedTypeReference<>() {
        }, 2L);
        // then
        ResponseDto<CartIdResponseDto> body = response.getBody();
    }
}
