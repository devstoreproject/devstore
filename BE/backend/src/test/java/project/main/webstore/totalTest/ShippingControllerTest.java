package project.main.webstore.totalTest;


import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.users.dto.ShippingInfoPostDto;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
public class ShippingControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    MockMvc mvc;
    @Autowired
    Gson gson;

    @Test
    @DisplayName("주소 등록 테스트")
    @WithMockCustomUser
    void post_info_test() throws Exception{
        // given
        ShippingInfoPostDto post = new ShippingInfoPostDto("김복자", "010-1313-1313", "540-20", "서울 특별시 종로구", "구미아파트 102호");
        String content = gson.toJson(post);
        // when
        mvc.perform(MockMvcRequestBuilders.post("/api/address").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(content));

//        template.postForEntity("/api/address",post, ResponseEntity.class)
        // then


    }
}
