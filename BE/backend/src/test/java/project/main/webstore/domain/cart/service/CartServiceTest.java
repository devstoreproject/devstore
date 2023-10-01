package project.main.webstore.domain.cart.service;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.cart.dto.LocalCartDto;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.repository.CartRepository;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.domain.item.exception.ItemExceptionCode;
import project.main.webstore.domain.item.service.OptionService;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.exception.BusinessLogicException;

@ExtendWith({MockitoExtension.class})
class CartServiceTest {
    @InjectMocks
    private CartService cartService;
    @Mock
    private CartRepository cartRepository;
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
        BDDMockito.given(userValidService.validUser(ArgumentMatchers.anyLong())).willReturn(userStub.createUser(1L));
        BDDMockito.given(optionService.getOptions(ArgumentMatchers.anyList())).willReturn(itemStub.createOptionListWithId());
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
        BDDMockito.given(userValidService.validUser(ArgumentMatchers.anyLong())).willReturn(userStub.createUser(1L));
        BDDMockito.given(optionService.getOptions(ArgumentMatchers.anyList())).willReturn(itemStub.createOptionListWithId());
        // when
        // then
        Assertions.assertThatThrownBy(()-> cartService.postCart(post,1L)).isInstanceOf(
                BusinessLogicException.class).hasMessage(ItemExceptionCode.ITEM_NOT_ENOUGH.getMessage());
    }


}


