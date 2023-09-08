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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.image.dto.ImageSortPostDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.security.dto.UserInfoDto;
import project.main.webstore.security.jwt.utils.JwtTokenizer;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class ItemControllerTest {
    final String DEFAULT_URL = "/api/items";
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    Gson gson;
    @Autowired
    MockMvc mvc;
    @Autowired
    JwtTokenizer jwtTokenizer;
    ItemStub itemStub = new ItemStub();
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("상품 등록")
    @WithMockCustomUser
    @Transactional
    void post_item_test() throws Exception {
        UserInfoDto userInfo = new UserInfoDto("2", "admin@gmailcom", "김복자", UserRole.ADMIN);
        String accessToken = jwtTokenizer.delegateAccessToken(userInfo);

        ItemPostDto imagePostDto = ItemPostDto.builder()
                .defaultCount(100)
                .itemPrice(1000000)
                .deliveryPrice(3000)
                .category(Category.COMPUTER)
                .discountRate(10)
                .name("맥북")
                .description("이것은 맥북입니다.")
                .optionList(List.of(new OptionPostRequestDto("옵션 세부 내역", 100, 10000, "옵션 이름"), new OptionPostRequestDto("옵션 세부 내역", 100, 10000, "옵션 이름")))
                .infoList(List.of(new ImageSortPostDto(1, true)))
                .build();
        String content = gson.toJson(imagePostDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setBearerAuth(accessToken);
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        Resource resource = new ClassPathResource("image/testImage.png");

        ByteArrayResource bytes = new ByteArrayResource(resource.getInputStream().readAllBytes()) {
            public String getFilename() {
                return "image.png";
            }
        };


        HttpHeaders partHeaders = new HttpHeaders();
        partHeaders.setContentType(MediaType.IMAGE_PNG);
        HttpEntity<ByteArrayResource> bytesPart = new HttpEntity<>(bytes, partHeaders);

        HttpHeaders partHeaders2 = new HttpHeaders();
        partHeaders2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> post2 = new HttpEntity<>(content, partHeaders2);

        body.add("post", post2);
        body.add("image", bytesPart);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        String url = "http://localhost:" + port + "/api/items";
        ResponseEntity<String> responseEntity = template.postForEntity(new URI(url), requestEntity, String.class);
        String body1 = responseEntity.getBody();
        System.out.println(body1);
    }

    @Test
    @DisplayName("수정 [이미지 없음] :성공")
    @WithMockCustomUser
    void patch_no_image_test() throws Exception {
        // given
        ItemPatchDto patch = itemStub.createPatchNoImage();
        String content = gson.toJson(patch);
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json", content.getBytes(StandardCharsets.UTF_8));
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1L).file(patchItem).accept(MediaType.APPLICATION_JSON));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }

    @Test
    @DisplayName("수정 :성공")
    @WithMockCustomUser
    void patch_image_test() throws Exception {
        // given
        ItemPatchDto patch = itemStub.createPatchWithImageTwo();
        String content = gson.toJson(patch);
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json", content.getBytes(StandardCharsets.UTF_8));


        Resource resource = new ClassPathResource("image/testImage.png");
        MockMultipartFile file1 = new MockMultipartFile("imageList", "originalFile.png", "png", resource.getInputStream());

        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1L).file(file1).file(file1).file(patchItem).accept(MediaType.APPLICATION_JSON));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }


    @Test
    @DisplayName("상품 삭제")
    @WithMockCustomUser
    void delete_image_test() throws Exception{
        // given

        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URL + "/{item-Id}", 1L));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
