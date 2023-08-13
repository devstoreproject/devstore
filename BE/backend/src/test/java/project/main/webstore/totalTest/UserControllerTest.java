package project.main.webstore.totalTest;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.main.webstore.domain.users.dto.UserPostRequestDto;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mvc;
    @Autowired
    Gson gson;
    @Test
    @DisplayName("사용자 Post")
    void postUserTest() throws Exception{
        // given
        UserPostRequestDto post = new UserPostRequestDto("admin@gmail.com", "asdffcx1111", "김송모자리", "010-8013-1313", "김송모");
        String content = gson.toJson(post);
        MockMultipartFile multipartFile = new MockMultipartFile("tmp", "TEST Mock".getBytes());
        MockMultipartFile postUser = new MockMultipartFile("post", "post", "application/json", content.getBytes(StandardCharsets.UTF_8));

        // when

        // then
        mvc.perform(MockMvcRequestBuilders.multipart("/api/users").file(multipartFile).file(postUser).accept(MediaType.APPLICATION_JSON))
                .andDo(log())
                .andExpect(jsonPath("$.data.userId").value(3L));
    }


}
