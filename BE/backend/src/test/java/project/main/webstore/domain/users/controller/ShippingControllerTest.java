package project.main.webstore.domain.users.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.users.dto.ShippingInfoPostDto;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.service.ShippingService;
import project.main.webstore.domain.users.stub.UserStub;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@Transactional
class ShippingControllerTest {
    static final String DEFAULT_URL = "/api/address";
    @Autowired
    MockMvc mvc;
    @Autowired
    Gson gson;
    @Autowired
    UserStub userStub;
    @MockBean
    ShippingService service;

    @Test
    @DisplayName("배송지 등록 테스트")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT)
    void post_ship_info_test() throws Exception{
        // given
        ShippingInfoPostDto post = userStub.createShipInfoPostDto();
        String content = gson.toJson(post);
        ShippingInfo shippingInfo = userStub.createShippingInfo();
        BDDMockito.given(service.postAddressInfo(any(ShippingInfo.class), anyLong())).willReturn(
                shippingInfo);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL).content(content)
                        .contentType(MediaType.APPLICATION_JSON));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.infoId").isNumber());
    }
}