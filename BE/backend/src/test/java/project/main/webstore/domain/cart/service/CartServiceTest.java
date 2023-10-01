package project.main.webstore.domain.cart.service;

import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.cart.enums.CartExceptionCode;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.domain.item.exception.ItemExceptionCode;
import project.main.webstore.domain.item.service.OptionService;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.exception.BusinessLogicException;

@ExtendWith({MockitoExtension.class})
class CartServiceTest {
    @InjectMocks
    private CartService cartService;
    @Mock
    private UserValidService userValidService;
    @Mock
    private OptionService optionService;
    private CartStub cartStub = new CartStub();
    private UserStub userStub = new UserStub();
    private ItemStub itemStub = new ItemStub();

    @Test
    @DisplayName("장바구니 등록 : 성공")
    void post_cart_test() throws Exception{
        // given
        List<LocalCartDto> post = cartStub.createLocalCartDtoList();
        given(userValidService.validUser(ArgumentMatchers.anyLong())).willReturn(userStub.createUser(1L));
        given(optionService.getOptions(ArgumentMatchers.anyList())).willReturn(itemStub.createOptionListWithId());
        // when
        Cart result  = cartService.postCart(post, 1L);
        // then
        Assertions.assertThat(result.getUser().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("장바구니 등록 : 실패 [제품 수량 부족]")
    void post_cart_fail_item_amount_not_enough_test() throws Exception{
        // given
        List<LocalCartDto> post = cartStub.createLocalCartDtoList();
        post.get(0).setCount(10001);
        given(userValidService.validUser(ArgumentMatchers.anyLong())).willReturn(userStub.createUser(1L));
        given(optionService.getOptions(ArgumentMatchers.anyList())).willReturn(itemStub.createOptionListWithId());
        // when
        // then
        Assertions.assertThatThrownBy(()-> cartService.postCart(post,1L)).isInstanceOf(
                BusinessLogicException.class).hasMessage(ItemExceptionCode.ITEM_NOT_ENOUGH.getMessage());
    }

    @Test
    @DisplayName("장바구니 수정 : 장바구니 상품 수량 수정")
    void patch_cart_item_count_chang_test() throws Exception{
        // given
        User user = userStub.createUser(1L);
        user.getCart().setCartItemList(cartStub.getCartItemListWithId());
        given(userValidService.validUser(anyLong())).willReturn(user);
        // when
        List<LocalCartDto> localCartPathList = cartStub.createLocalCartPathList();
        Cart result = cartService.patchCart(1L, localCartPathList,
                new ArrayList<>());
        // then
        Assertions.assertThat(result.getCartItemList().get(0).getItemCount()).isEqualTo(localCartPathList.get(0).getCount());
    }

    @Test
    @DisplayName("장바구니 수정 : 장바구니 없음 실페")
    void patch_cart_not_found_cart_test() throws Exception{
        // given
        User user = userStub.createUser(1L);
        user.setCart(null);
        given(userValidService.validUser(anyLong())).willReturn(user);
        // when
        Assertions.assertThatThrownBy(() ->  cartService.patchCart(1L, cartStub.createLocalCartPathList(), new ArrayList<>())).isInstanceOf(
                BusinessLogicException.class).hasMessage(CartExceptionCode.Cart_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("장바구니 수정 : 장바구니 삭제 수정")
    void patch_cart_item_delete_test() throws Exception{
        // given
        User user = userStub.createUser(1L);
        user.getCart().setCartItemList(new ArrayList<>(cartStub.getCartItemListWithId()));
        given(userValidService.validUser(anyLong())).willReturn(user);
        // when
        Cart cart = cartService.patchCart(1L, new ArrayList<>(), List.of(1L));
        // then
        Assertions.assertThat(cart.getCartItemList().stream().map(CartItem::getId)).doesNotContain(1L);
    }
    @Test
    @DisplayName("장바구니 수정 : 특정 상품 삭제 및 특정 상품 수량 변경 성공")
    void patch_cart_test() throws Exception{
        // given
        User user = userStub.createUser(1L);
        user.getCart().setCartItemList(new ArrayList<>(cartStub.getCartItemListWithId()));
        given(userValidService.validUser(anyLong())).willReturn(user);
        // when
        Cart cart = cartService.patchCart(1L, cartStub.createLocalCartPathList(), List.of(3L));
        // then
        Assertions.assertThat(cart.getCartItemList().stream().map(CartItem::getId)).doesNotContain(3L);
        Assertions.assertThat(cart.getCartItemList().get(0).getItemCount()).isEqualTo(5L);
    }

    @Test
    @DisplayName("장바구니 조회")
    void get_cart_test() throws Exception{
        // given
        given(userValidService.validUser(anyLong())).willReturn(userStub.createUser(1L));
        // when
        Cart result = cartService.getCart(1L);
        // then
        Assertions.assertThat(result).isNotNull();
    }
}
