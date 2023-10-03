package project.main.webstore.totalTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
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
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import project.main.webstore.domain.order.dto.OrderIdResponseDto;
import project.main.webstore.domain.order.dto.OrderPatchDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.stub.OrderStub;
import project.main.webstore.dto.ResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderControllerTest {

    @Container
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8");
    private final String URL = "http://localhost:";
    private final String DEFAULT_URL = "/api/orders";
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    Gson gson;
    @LocalServerPort
    private int port;
    @Autowired
    private OrderStub orderStub;

    @Test
    @DisplayName("상품 주문")
    void post_order_test() {
        String url = URL + port + DEFAULT_URL;

        OrderPostDto post = orderStub.createOrderPost();

        HttpHeaders headers = orderStub.getLoginHeader(orderStub.getJWTAccessTokenClient());

        HttpEntity<OrderPostDto> request = new HttpEntity<>(post, headers);

        ResponseEntity<String> response = template.postForEntity(url, request, String.class);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<OrderIdResponseDto>>() {
        }.getType();
        ResponseDto<OrderIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(responseDto.getData().getOrderId()).isEqualTo(2L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("상품 수정 테스트")
    void order_patch_test() throws Exception {
        // given
        OrderPatchDto patch = new OrderPatchDto("수정할 메시지", 1L);
        HttpHeaders headers = orderStub.getLoginHeader(orderStub.getJWTAccessTokenClient());
        HttpEntity<OrderPatchDto> request = new HttpEntity<>(patch, headers);
        String url = URL + port + DEFAULT_URL + "/{order-id}";
        // when
        ResponseEntity<String> response = template.exchange(url, HttpMethod.PATCH, request,
                String.class, 1L);
        String body = response.getBody();
        // then
        Type responseType = new TypeToken<ResponseDto<OrderIdResponseDto>>() {
        }.getType();
        ResponseDto<OrderIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(responseDto.getData().getOrderId()).isEqualTo(1L);
    }
    @Test
    @DisplayName("상품 수정 테스트 : 상품 수정 확인 테스트")
    void order_patch_check_chang_by_get_test() throws Exception {
        // given
        OrderPatchDto patch = new OrderPatchDto("수정할 메시지", 1L);
        HttpHeaders headers = orderStub.getLoginHeader(orderStub.getJWTAccessTokenClient());
        HttpEntity<OrderPatchDto> patchRequest = new HttpEntity<>(patch, headers);
        HttpEntity<Object> getRequest = new HttpEntity<>(headers);
        String url = URL + port + DEFAULT_URL + "/{order-id}";
        // when
        template.exchange(url, HttpMethod.PATCH, patchRequest,
                String.class, 1L);
        ResponseEntity<ResponseDto> response = template.exchange(url, HttpMethod.GET, getRequest,
                ResponseDto.class, 1L);
        // then
        //TODO : 프론트와 협의 후 수정 필
        ResponseDto body = response.getBody();
        LinkedHashMap data = (LinkedHashMap) body.getData();

        Assertions.assertThat(data.get("message")).isEqualTo("수정할 메시지");
    }
}
