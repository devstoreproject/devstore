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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import project.main.webstore.domain.cart.dto.CartGetResponseDto;
import project.main.webstore.domain.cart.dto.CartIdResponseDto;
import project.main.webstore.domain.cart.dto.CartPatchRequestDto;
import project.main.webstore.domain.cart.dto.CartPostRequestDto;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.helper.TestUtils;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
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
    void cart_patch_chang_item_count_test() throws Exception{
        // given
        HttpHeaders headers = testUtils.getJWTClient();
        CartPatchRequestDto patch = cartStub.getCartPatchItemOnlyCountChang();

        HttpEntity<CartPatchRequestDto> request = new HttpEntity<>(patch,headers);
        String url = URL + port + DEFAULT_URL;

        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PATCH, request,
                String.class);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<CartIdResponseDto>>() {}.getType();
        ResponseDto<CartIdResponseDto> responseDto = gson.fromJson(body, responseType);
        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getUserId()).isEqualTo(1L);
        Assertions.assertThat(responseDto.getData().getCartId()).isNotNull();
    }

    @Test
    @DisplayName("장바구니 제품 수량 변경")
    void cart_patch_delete_item_test() throws Exception{
        // given
        HttpHeaders headers = testUtils.getJWTClient();
        CartPatchRequestDto patch = cartStub.getCartPatchItemOnlyDelete();

        HttpEntity<CartPatchRequestDto> request = new HttpEntity<>(patch,headers);
        String url = URL + port + DEFAULT_URL;

        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PATCH, request,
                String.class);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<CartIdResponseDto>>() {}.getType();
        ResponseDto<CartIdResponseDto> responseDto = gson.fromJson(body, responseType);
        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getUserId()).isEqualTo(1L);
        Assertions.assertThat(responseDto.getData().getCartId()).isNotNull();
    }

    @Test
    @DisplayName("장바구니 제품 수량 변경")
    void cart_patch_test() throws Exception{
        // given
        HttpHeaders headers = testUtils.getJWTClient();
        CartPatchRequestDto patch = cartStub.getCartPatchItem();

        HttpEntity<CartPatchRequestDto> request = new HttpEntity<>(patch,headers);
        String url = URL + port + DEFAULT_URL;

        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PATCH, request,
                String.class);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<CartIdResponseDto>>() {}.getType();
        ResponseDto<CartIdResponseDto> responseDto = gson.fromJson(body, responseType);
        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getUserId()).isEqualTo(1L);
        Assertions.assertThat(responseDto.getData().getCartId()).isNotNull();
    }

    @Test
    @DisplayName("장바구니 조회")
    void cart_get_test() throws Exception{
        // given
        HttpHeaders headers = testUtils.getJWTClient();
        String url = URL + port + DEFAULT_URL + "/users/{userId}";

        HttpEntity<Object> request = new HttpEntity<>(headers);
        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, request,
                String.class, 1L);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<CartGetResponseDto>>() {}.getType();
        ResponseDto<CartGetResponseDto> responseDto = gson.fromJson(body, responseType);
        // then
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getCartId()).isEqualTo(1L);
        Assertions.assertThat(responseDto.getData().getDeliveryPrice()).isEqualTo(3000);
        Assertions.assertThat(responseDto.getData().getDiscountedPrice()).isEqualTo(15200000);
        Assertions.assertThat(responseDto.getData().getItemList()).isNotNull();

    }
}
