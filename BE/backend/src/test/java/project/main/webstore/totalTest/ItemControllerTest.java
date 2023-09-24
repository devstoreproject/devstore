package project.main.webstore.totalTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import project.main.webstore.domain.item.dto.ItemIdResponseDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.dto.ResponseDto;

/*
 * DB에 저장 되어 있는 사용자 ID : 1L 클라이언트 2L 어드민   asdffcx1111
 * 상품 생성은 상품을 생성한 뒤 삭제하는 로직을 통해 구현한다.
 * 상품 수정 및 조회는 기존에 가지고 있는 데이터를 활용해서 구현한다.
 * 저장된 item 2L : 이미지를 가지고 있음, 3L : 이미지가 없음
 * item 2L 에 연결된 옵션들 : 옵션 1L, 옵션 2L
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class ItemControllerTest {

    final String URL = "http://localhost:";
    final String DEFAULT_URL = "/api/items";
    TestRestTemplate clientLoginTest = new TestRestTemplate("client@gmail.com", "asdffcx1111");
    TestRestTemplate adminLoginTest = new TestRestTemplate("admin@gmail.com", "asdffcx1111");
    TestRestTemplate noLoginTest = new TestRestTemplate();
    Long itemId;
    @Autowired
    Gson gson;
    ItemStub itemStub = new ItemStub();
    @LocalServerPort
    private int port;

    @AfterEach
    void after() {
        String url = URL + port + DEFAULT_URL;
        System.out.println("### DELETE RESULT ItemId = " + itemId);
        adminLoginTest.delete(url, itemId);
    }

    @Test
    @DisplayName("상품 등록 : 이미지 포함")
    void post_item_test() throws Exception {
        ItemPostDto post = itemStub.createPostDtoWithImage();
        String content = gson.toJson(post);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                itemStub.getMultipartTwoImageAndJsonDataRequest(content);

        String url = "http://localhost:" + port + "/api/items";
        System.out.println("url = " + url);
        ResponseEntity<String> response = adminLoginTest.postForEntity(new URI(url),
                requestEntity, String.class);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<ItemIdResponseDto>>() {
        }.getType();
        ResponseDto<ItemIdResponseDto> responseDto = gson.fromJson(body, responseType);
        itemId = responseDto.getData().getItemId();

        Assertions.assertThat(responseDto.getCode()).isEqualTo("C201");
        Assertions.assertThat(responseDto.getMessage()).isEqualTo("생성 완료");
        Assertions.assertThat(responseDto.getData().getItemId()).isNotNull();
        System.out.println("##### itemId = " + itemId);
    }

    @Test
    @DisplayName("상품 등록 : 이미지 없음")
    void post_item_no_image_test() throws Exception {
        ItemPostDto post = itemStub.createPostDtoNoImage();
        String content = gson.toJson(post);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                itemStub.getMultipartJsonDataRequest(content);

        String url = "http://localhost:" + port + "/api/items";
        System.out.println("url = " + url);
        ResponseEntity<String> response = adminLoginTest.postForEntity(new URI(url),
                requestEntity, String.class);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<ItemIdResponseDto>>() {
        }.getType();
        ResponseDto<ItemIdResponseDto> responseDto = gson.fromJson(body, responseType);
        itemId = responseDto.getData().getItemId();

        Assertions.assertThat(responseDto.getCode()).isEqualTo("C201");
        Assertions.assertThat(responseDto.getMessage()).isEqualTo("생성 완료");
        Assertions.assertThat(responseDto.getData().getItemId()).isNotNull();
        System.out.println("##### itemId = " + itemId);
    }

    @Test
    @DisplayName("상품 수정 : 이미지 없음")
    void patch_item_no_image_test() throws Exception {
        //상품 조회

    }

    private void requestItemGet() {
        String url = "http://localhost:" + port + "/api/items/{itemId}";
        ResponseEntity<String> getResponse = adminLoginTest.getForEntity(url, String.class, itemId);
        Type responseType = new TypeToken<ResponseDto<ItemResponseDto>>() {
        }.getType();
        ResponseDto<ItemResponseDto> getResponseDto = gson.fromJson(getResponse.getBody(),
                responseType);
    }

    private void requestItemPost()
            throws IOException, URISyntaxException {
        ItemPostDto post = itemStub.createPostDtoWithImage();
        String content = gson.toJson(post);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                itemStub.getMultipartTwoImageAndJsonDataRequest(content);

        String url = "http://localhost:" + port + "/api/items";
        System.out.println("url = " + url);
        ResponseEntity<String> response = adminLoginTest.postForEntity(new URI(url),
                requestEntity, String.class);
        String body = response.getBody();
        Type responseType = new TypeToken<ResponseDto<ItemIdResponseDto>>() {
        }.getType();
        ResponseDto<ItemIdResponseDto> responseDto = gson.fromJson(body, responseType);
        itemId = responseDto.getData().getItemId();
    }


}
