package project.main.webstore.domain.order.service;

import static org.mockito.ArgumentMatchers.anyLong;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.order.repository.OrderRepository;
import project.main.webstore.domain.order.stub.OrderStub;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.valueObject.Address;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserValidService userService;
    private UserStub userStub = new UserStub();
    private CartStub cartStub = new CartStub();
    private OrderStub orderStub = new OrderStub();
    @Test
    @DisplayName("주문 생성 테스트 : 주문 상품 없음 으로 인한 실패")
    void create_order_fail_test() {
        //given
        User user = userStub.createUser(1L);
        ShippingInfo info = new ShippingInfo(1L, "김복자", new Address("123-45", "대한민국", "우리집", "010-1234-6789"), user);
        user.getShippingInfoList().add(info);

        Cart cart = new Cart(user);
        user.setCart(cart);

        OrderLocalDto post = new OrderLocalDto("안녕", 1L, 1L, 1L, 1L,new ArrayList<>());

        //when
        BDDMockito.given(userService.validUserAllInfo(anyLong())).willReturn(user);

        Assertions.assertThatThrownBy(() -> orderService.createOrder(post)).isInstanceOf(
                BusinessLogicException.class).hasMessage(OrderExceptionCode.ORDER_ITEM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("주문 생성 테스트 : 주문 상품 없음 으로 인한 실패")
    void create_order_fail_cart_item_not_exist_test() {
        //given
        User user = userStub.createUser(1L);
        ShippingInfo info = new ShippingInfo(1L, "김복자", new Address("123-45", "대한민국", "우리집", "010-1234-6789"), user);
        user.getShippingInfoList().add(info);

        Cart cart = new Cart(user);
        user.setCart(cart);

        OrderLocalDto post = new OrderLocalDto("안녕", 1L, 1L, 1L, 1L, List.of(1L));

        Orders order = new Orders(post.getMessage(), cart, user, info);

        //when
        BDDMockito.given(userService.validUserAllInfo(anyLong())).willReturn(user);

        Assertions.assertThatThrownBy(() -> orderService.createOrder(post)).isInstanceOf(
                BusinessLogicException.class).hasMessage(OrderExceptionCode.ORDER_ITEM_NOT_FOUND.getMessage());
        //then
    }

    @Test
    @DisplayName("주문 생성 테스트 : 성공")
    void create_order_test() {
        //given
        User user = userStub.createUserWithShippingInfo(1L);

        Cart cart = new Cart(user);
        List<CartItem> cartItemList = new ArrayList<>(cartStub.getCartItemListWithId());

        cart.setCartItemList(cartItemList);
        user.setCart(cart);

        OrderLocalDto post = new OrderLocalDto("안녕", 1L, 1L, 1L, 1L, List.of(1L));

        Orders order = new Orders(post.getMessage(), cart, user, user.getShippingInfo(1L));

        //when
        BDDMockito.given(userService.validUserAllInfo(anyLong())).willReturn(user);
        BDDMockito.given(orderRepository.save(ArgumentMatchers.any(Orders.class))).willReturn(order);

        Orders result = orderService.createOrder(post);
        //then
        Assertions.assertThat(result.getRecipient()).isEqualTo(user.getShippingInfo(1L).getRecipient());
        Assertions.assertThat(result.getMessage()).isEqualTo(post.getMessage());
        Assertions.assertThat(result.getOrdersStatus()).isEqualTo(OrdersStatus.ORDER_COMPLETE);
    }



    @Test
    @DisplayName("주문 수정 테스트 : 성공")
    void patch_order_test() throws Exception{
        // given
        OrderLocalDto patch = new OrderLocalDto("수정한 메시지", 1L, 1L, 1L, 1L, List.of(1L));
        Orders order = orderStub.createOrder(1L);
        BDDMockito.given(orderRepository.findAllByOrderId(anyLong())).willReturn(Optional.of(order));
        // when
        Orders orders = orderService.editOrder(patch, 1L);
        // then
        Assertions.assertThat(orders.getMessage()).isEqualTo("수정한 메시지");
    }



    private String createOrderNumber() {
        Calendar cal = Calendar.getInstance();

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);

        StringBuilder builder = new StringBuilder();
        builder.append(y).append(m).append(d);

        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        return builder.toString();
    }
}