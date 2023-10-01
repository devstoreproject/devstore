package project.main.webstore.totalTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import project.main.webstore.domain.cart.dto.CartDeleteDto;
import project.main.webstore.domain.cart.dto.CartIdResponseDto;
import project.main.webstore.domain.cart.dto.CartItemDto;
import project.main.webstore.domain.cart.dto.CartPatchRequestDto;
import project.main.webstore.domain.cart.dto.CartPostRequestDto;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.helper.TestUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class CartControllerTest {
    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8");
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    Gson gson;
    @Autowired
    TestUtils testUtils;
    @LocalServerPort
    private int port;
    final String URL = "http://localhost:";
    @Autowired
    private CartStub cartStub;
    private String DEFAULT_URL = "/api/cart";

    @Test
    @DisplayName("카트 추가")
    void post_cart_test(){
        CartPostRequestDto post = cartStub.getCartPostDto();
        String url = URL + port + DEFAULT_URL;
        HttpHeaders headers = testUtils.getJWTClient();
        HttpEntity<CartPostRequestDto> requestEntity = new HttpEntity<>(post, headers);

        ResponseEntity<String> response = template.postForEntity(url, requestEntity,
                String.class);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<CartIdResponseDto>>() {}.getType();
        ResponseDto<CartIdResponseDto> responseDto = gson.fromJson(body, responseType);
        Assertions.assertThat(responseDto.getData().getCartId()).isNotNull();
        Assertions.assertThat(responseDto.getData().getUserId()).isEqualTo(1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }




    @Test
    @DisplayName("장바구니 제품 수량 변경")
    void cart_patch_test() throws Exception{
        // given
        HttpHeaders headers = testUtils.getJWTClient();
        CartPatchRequestDto patch = new CartPatchRequestDto(List.of(new CartItemDto(25L, 10), new CartItemDto(26L, 10)),null);
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
