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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.dto.ResponseDto;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    MockMvc mvc;
    @Autowired
    Gson gson;
    @LocalServerPort
    private int port;

    @Test
    @DisplayName("사용자 Post")
    void postUserTest() throws Exception {
        // given
        UserPostRequestDto post = new UserPostRequestDto("admin1@gmail.com", "asdffcx1111", "김송모자리", "010-8013-1313", "김송모");
        String content = gson.toJson(post);
        MockMultipartFile multipartFile = new MockMultipartFile("tmp", "TEST Mock".getBytes());
        MockMultipartFile postUser = new MockMultipartFile("post", "post", "application/json", content.getBytes(StandardCharsets.UTF_8));

        // when

        // then
        mvc.perform(MockMvcRequestBuilders.multipart("/api/users").file(multipartFile).file(postUser).accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(jsonPath("$.data.userId").value(3L));
    }

    @Test
    @DisplayName("사용자 POST [TestRestTemplate]")
    void post_user_template_test() throws Exception {
        UserPostRequestDto post = new UserPostRequestDto("admin221@gmail.com", "asdffcx1111", "김송모자리", "010-8013-1313", "김송모");
        String content = gson.toJson(post);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        Resource resource = new ClassPathResource("image/testImage.png");

        ByteArrayResource bytes = new ByteArrayResource(resource.getInputStream().readAllBytes()) {
            public String getFilename() {
                return "image.png";
            }
        };


        HttpHeaders partHeaders = new HttpHeaders();
        partHeaders.setContentType(MediaType.IMAGE_PNG);
        HttpEntity<ByteArrayResource> bytesPart = new HttpEntity<ByteArrayResource>(bytes, partHeaders);

        HttpHeaders partHeaders2 = new HttpHeaders();
        partHeaders2.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> post2 = new HttpEntity<>(content, partHeaders2);

        body.add("post", post2);
        body.add("image", bytesPart);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        String url = "http://localhost:" + port + "/api/users";
        ResponseEntity<ResponseDto> responseEntity = template.postForEntity(new URI(url), requestEntity, ResponseDto.class);
        System.out.println(responseEntity);
    }
    //TODO: 리펙토링 절실하게 필요
    //Multipart 에서 각각 헤더 형식을 지정해서 잡아야할 경우가 있다.
    //TODO: 검증을 위한 코드 구현 필요


}
