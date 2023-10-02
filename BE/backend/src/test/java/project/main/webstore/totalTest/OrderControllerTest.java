package project.main.webstore.totalTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import project.main.webstore.domain.order.dto.OrderIdResponseDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.stub.OrderStub;
import project.main.webstore.dto.ResponseDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class OrderControllerTest {
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
        //토큰 발급
        //url 설정
        String url = "http://localhost:" + port + "/api/orders";

        OrderPostDto post = orderStub.createOrderPost();

        HttpHeaders headers = orderStub.getLoginHeader(orderStub.getJWTAccessTokenClient());

        HttpEntity<OrderPostDto> request = new HttpEntity<>(post,headers);


        ResponseEntity<String> response = template.postForEntity(url, request, String.class);
        String body = response.getBody();

        Type responseType = new TypeToken<ResponseDto<OrderIdResponseDto>>() {}.getType();
        ResponseDto<OrderIdResponseDto> responseDto = gson.fromJson(body, responseType);

        Assertions.assertThat(responseDto.getData().getOrderId()).isEqualTo(1L);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
}
