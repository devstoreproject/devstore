package project.main.webstore.domain.order.controller;


import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.service.OrderService;
import project.main.webstore.domain.order.stub.OrderStub;
import project.main.webstore.domain.users.enums.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private Gson gson;
    @MockBean
    private OrderService orderService;
    @Autowired
    private OrderStub orderStub;

    private String DEFAULT_URL = "/api/orders";

    @Test
    @DisplayName("주문 하기 테스트")
    @WithMockCustomUser(role = "CLIENT",userRole = UserRole.CLIENT,userId = 1L)
    void post_order_test() throws Exception{
        // given
        OrderPostDto post = orderStub.createOrderPost();
        String content = gson.toJson(post);

        BDDMockito.given(orderService.createOrder(ArgumentMatchers.any(OrderLocalDto.class))).willReturn(orderStub.createOrder(1L));
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L));
    }
    

}

