package project.main.webstore.domain.order.service;

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
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.repository.OrderRepository;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.valueObject.Address;

import java.util.Calendar;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserValidService userService;
    private UserStub userStub = new UserStub();

    @Test
    @DisplayName("주문 생성 테스트")
    void createOrderTest() {
        //given
        User user = userStub.createUser(1L);
        ShippingInfo info = new ShippingInfo(1L, "김복자", new Address("123-45", "대한민국", "우리집", "010-1234-6789"), user);
        user.getShippingInfoList().add(info);

        Cart cart = new Cart(user);
        user.setCart(cart);

        OrderLocalDto post = new OrderLocalDto("안녕", 1L, 1L, 1L, 1L);

        Orders order = new Orders(post.getMessage(), cart, user, info);

        //when
        //mocking
        BDDMockito.given(userService.validUserAllInfo(ArgumentMatchers.anyLong())).willReturn(user);
        BDDMockito.given(orderRepository.save(ArgumentMatchers.any(Orders.class))).willReturn(order);

        Orders result = orderService.createOrder(post, 1L);

        //then
        Assertions.assertThat(result.getRecipient()).isEqualTo(info.getRecipient()); //비교검증
        Assertions.assertThat(result.getOrderId()).isNull();
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