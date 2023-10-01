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
import project.main.webstore.domain.cart.dto.CartPatchRequestDto;
import project.main.webstore.domain.cart.dto.CartPostRequestDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.service.CartService;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.stub.ItemStub;
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
    @Autowired
    private ItemStub itemStub;
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

    @Test
    @DisplayName("장바구니 상품 수량 변경 : 성공")
    @WithMockCustomUser(role = "CLIENT",userRole = UserRole.CLIENT,userId = 1L)
    void patch_cart_item_count_test() throws Exception{
        // given
        CartPatchRequestDto patch = cartStub.getCartPatchItemOnlyCountChang();
        String content = gson.toJson(patch);

        given(cartService.patchCart(anyLong(),anyList(),anyList())).willReturn(cartStub.getCart(1L));
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch(DEFAULT_URL).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cartId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1L));
    }

    @Test
    @DisplayName("장바구니 장바구니 상품 제거 : 성공")
    @WithMockCustomUser(role = "CLIENT",userRole = UserRole.CLIENT,userId = 1L)
    void patch_cart_item_delete_test() throws Exception{
        // given
        CartPatchRequestDto patch = cartStub.getCartPatchItemOnlyDelete();
        String content = gson.toJson(patch);

        given(cartService.patchCart(anyLong(),anyList(),anyList())).willReturn(cartStub.getCart(1L));
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch(DEFAULT_URL).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cartId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1L));
    }

    @Test
    @DisplayName("장바구니 수정 : 성공")
    @WithMockCustomUser(role = "CLIENT",userRole = UserRole.CLIENT,userId = 1L)
    void patch_cart_test() throws Exception{
        // given
        CartPatchRequestDto patch = cartStub.getCartPatchItem();
        String content = gson.toJson(patch);

        given(cartService.patchCart(anyLong(),anyList(),anyList())).willReturn(cartStub.getCart(1L));
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.patch(DEFAULT_URL).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.cartId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1L));
    }

    @Test
    @DisplayName("장바구니 조회")
    void get_cart_test() throws Exception{
        // given

        Cart cart = cartStub.getCartWithCartItemId(1L);
        ItemOption option = itemStub.createOption(1L);
        option.setItem(itemStub.createItem(1L));
        cart.getCartItemList().forEach(cartItem -> cartItem.setOption(option));

        given(cartService.getCart(anyLong())).willReturn(cart);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/users/{userId}", 1L));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


}

