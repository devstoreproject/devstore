package project.main.webstore.domain.cart.controller;

import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import project.main.webstore.domain.cart.dto.CartPostRequestDto;
import project.main.webstore.domain.cart.service.CartService;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.domain.users.enums.UserRole;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class CartControllerTest {
    private final String DEFAULT_URL = "/api/cart";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private Gson gson;
    @Autowired
    private CartStub cartStub;
    @MockBean
    private CartService cartService;

    @Test
    @DisplayName("장바구니 등록 : 성공")
    @WithMockCustomUser(role = "CLIENT",userRole = UserRole.CLIENT,userId = 1L)
    void post_cart_test() throws Exception{
        CartPostRequestDto cartPostDto = cartStub.getCartPostDto();
        String content = gson.toJson(cartPostDto);
        given(cartService.postCart(anyList(),anyLong())).willReturn(cartStub.getCart(1L));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL).content(content));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cartId").value(1L));
    }
}