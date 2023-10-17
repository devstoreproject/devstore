package project.main.webstore.domain.order.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static project.main.webstore.domain.order.enums.OrdersStatus.DELIVERY_COMPLETE;
import static project.main.webstore.domain.order.enums.OrdersStatus.ORDER_CANCEL;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.cart.stub.CartStub;
import project.main.webstore.domain.order.dto.OrderDBDailyPriceDto;
import project.main.webstore.domain.order.dto.OrderDBItemSaleDto;
import project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.order.repository.OrderRepository;
import project.main.webstore.domain.order.stub.OrderStub;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.exception.UserExceptionCode;
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
        ShippingInfo info = new ShippingInfo(1L, "김복자",
                new Address("123-45", "대한민국", "우리집", "010-1234-6789"), user);
        user.getShippingInfoList().add(info);

        Cart cart = new Cart(user);
        user.setCart(cart);

        OrderLocalDto post = new OrderLocalDto("안녕", 1L, 1L, 1L, 1L, new ArrayList<>());

        //when
        given(userService.validUserAllInfo(anyLong())).willReturn(user);

        Assertions.assertThatThrownBy(() -> orderService.createOrder(post)).isInstanceOf(
                        BusinessLogicException.class)
                .hasMessage(OrderExceptionCode.ORDER_ITEM_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("주문 생성 테스트 : 주문 상품 없음 으로 인한 실패")
    void create_order_fail_cart_item_not_exist_test() {
        //given
        User user = userStub.createUser(1L);
        ShippingInfo info = new ShippingInfo(1L, "김복자",
                new Address("123-45", "대한민국", "우리집", "010-1234-6789"), user);
        user.getShippingInfoList().add(info);

        Cart cart = new Cart(user);
        user.setCart(cart);

        OrderLocalDto post = new OrderLocalDto("안녕", 1L, 1L, 1L, 1L, List.of(1L));

        Orders order = new Orders(post.getMessage(), cart, user, info);

        //when
        given(userService.validUserAllInfo(anyLong())).willReturn(user);

        Assertions.assertThatThrownBy(() -> orderService.createOrder(post)).isInstanceOf(
                        BusinessLogicException.class)
                .hasMessage(OrderExceptionCode.ORDER_ITEM_NOT_FOUND.getMessage());
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
        given(userService.validUserAllInfo(anyLong())).willReturn(user);
        given(orderRepository.save(ArgumentMatchers.any(Orders.class))).willReturn(order);

        Orders result = orderService.createOrder(post);
        //then
        Assertions.assertThat(result.getRecipient())
                .isEqualTo(user.getShippingInfo(1L).getRecipient());
        Assertions.assertThat(result.getMessage()).isEqualTo(post.getMessage());
        Assertions.assertThat(result.getOrdersStatus()).isEqualTo(OrdersStatus.ORDER_COMPLETE);
    }


    @Test
    @DisplayName("주문 수정 테스트 : 성공")
    void patch_order_test() throws Exception {
        // given
        OrderLocalDto patch = new OrderLocalDto("수정한 메시지", 1L, 1L, 1L, 1L, List.of(1L));
        Orders order = orderStub.createOrder(1L);
        given(orderRepository.findAllByOrderId(anyLong())).willReturn(Optional.of(order));
        // when
        Orders orders = orderService.editOrder(patch, 1L);
        // then
        Assertions.assertThat(orders.getMessage()).isEqualTo("수정한 메시지");
    }

    @Test
    @DisplayName("주문 id 조회 : 성공")
    void get_order_by_id_test() throws Exception {
        // given
        given(userService.validUser(anyLong())).willReturn(userStub.createUser(1L));
        given(orderRepository.findById(anyLong())).willReturn(
                Optional.of(orderStub.createOrder(2L)));
        // when
        Orders order = orderService.getOrder(2L, 1L);
        // then
        Assertions.assertThat(order.getOrderId()).isEqualTo(2L);
        Assertions.assertThat(order.getUser().getId()).isEqualTo(1L);
        Assertions.assertThat(order.getMessage()).isEqualTo("메시지");
    }

    @Test
    @DisplayName("주문 orderNumber 조회 : 성공")
    void get_order_by_order_number_test() throws Exception {
        // given
        given(userService.validUser(anyLong())).willReturn(userStub.createUser(1L));
        Orders orderStub = this.orderStub.createOrder(2L);
        given(orderRepository.findAllByOrderNumber(anyString())).willReturn(Optional.of(orderStub));
        // when
        Orders order = orderService.getOrder(orderStub.getOrderNumber(), 1L);
        // then
        Assertions.assertThat(order.getOrderId()).isEqualTo(2L);
        Assertions.assertThat(order.getUser().getId()).isEqualTo(1L);
        Assertions.assertThat(order.getOrderNumber()).isEqualTo(orderStub.getOrderNumber());
        Assertions.assertThat(order.getMessage()).isEqualTo("메시지");
    }

    @Test
    @DisplayName("주문 orderNumber 조회 : 실패 [본인 / 관리자 아님]")
    void get_order_by_order_number_fail_test() throws Exception {
        // given
        Orders orderStub = this.orderStub.createOrder(2L);

        given(userService.validUser(anyLong())).willReturn(userStub.createUser(2L));
        given(orderRepository.findAllByOrderNumber(anyString())).willReturn(Optional.of(orderStub));
        // when
        Assertions.assertThatThrownBy(() -> orderService.getOrder(orderStub.getOrderNumber(), 1L))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage(UserExceptionCode.USER_NOT_ACCESS.getMessage());
    }

    @Test
    @DisplayName("주문 orderNumber 조회 : 성공[관리자]")
    void get_order_by_order_number_admin_test() throws Exception {
        // given
        User userAdmin = userStub.createUserAdmin();
        Orders orderStub = this.orderStub.createOrder(2L);
        orderStub.setUser(userAdmin);
        given(userService.validUser(anyLong())).willReturn(userAdmin);
        given(orderRepository.findAllByOrderNumber(anyString())).willReturn(Optional.of(orderStub));
        Orders order = orderService.getOrder(orderStub.getOrderNumber(), userAdmin.getId());
        // when
        Assertions.assertThat(order.getOrderId()).isEqualTo(2L);
        Assertions.assertThat(order.getUser().getId()).isEqualTo(userAdmin.getId());
        Assertions.assertThat(order.getUser().getUserRole()).isEqualTo(UserRole.ADMIN);
        Assertions.assertThat(order.getOrderNumber()).isEqualTo(orderStub.getOrderNumber());
        Assertions.assertThat(order.getMessage()).isEqualTo("메시지");
    }

    @Test
    @DisplayName("주문 전체 조회 : 관리자 아님")
    void get_order_page_not_admin_test() throws Exception {
        // given
        Assertions.assertThatThrownBy(() -> orderService.getOrders(orderStub.getPage(), -1L, null))
                .isInstanceOf(
                        BusinessLogicException.class)
                .hasMessage(UserExceptionCode.USER_NOT_LOGIN.getMessage());
    }

    @Test
    @DisplayName("주문 전체 조회 : 관리자")
    void get_order_page_test() throws Exception {
        int month = 1;
        given(orderRepository.findByMonth(any(Pageable.class),
                any(LocalDateTime.class))).willReturn(orderStub.createOrderWithMonthPage(month));
        Page<Orders> orders = orderService.getOrders(orderStub.getPage(), 0L, month);

        Assertions.assertThat(orders.map(order -> order.getCreatedAt().getMonth())).containsOnly(
                Month.JANUARY);
    }

    @Test
    @DisplayName("주문 전체 조회 : 관리자")
    void get_order_page_all_test() throws Exception {
        given(orderRepository.findAll(any(Pageable.class))).willReturn(orderStub.createOrderPage());
        Page<Orders> orders = orderService.getOrders(orderStub.getPage(), 0L, null);

        Assertions.assertThat(orders.getContent().stream().map(Orders::getOrderId))
                .contains(1L, 2L, 3L, 4L, 5L);
        Assertions.assertThat(orders.getPageable().getPageNumber()).isZero();
        Assertions.assertThat(orders.getPageable().getPageSize()).isEqualTo(30);
    }

    @Test
    @DisplayName("주문 월별 조회 : 고객")
    void get_order_page_client_test() throws Exception {
        int month = 1;
        given(orderRepository.findByMonth(any(Pageable.class),
                any(LocalDateTime.class))).willReturn(orderStub.createOrderWithMonthPage(month));
        Page<Orders> orders = orderService.getOrders(orderStub.getPage(), 0L, month);

        Assertions.assertThat(orders.map(order -> order.getCreatedAt().getMonth())).containsOnly(
                Month.JANUARY);
        Assertions.assertThat(orders.map(order -> order.getUser().getId())).containsOnly(1L);
    }

    @Test
    @DisplayName("주문 전체 조회 : 고객")
    void get_order_page_all_client_test() throws Exception {
        given(orderRepository.findAll(any(Pageable.class))).willReturn(orderStub.createOrderPage());
        Page<Orders> orders = orderService.getOrders(orderStub.getPage(), 0L, null);

        Assertions.assertThat(orders.map(order -> order.getUser().getId())).containsOnly(1L);
        Assertions.assertThat(orders.getPageable().getPageNumber()).isZero();
        Assertions.assertThat(orders.getPageable().getPageSize()).isEqualTo(30);
    }

    @Test
    @DisplayName("월별 상품 조회")
    void get_month_price_test() throws Exception {
        // given
        given(orderRepository.monthlyPrice()).willReturn(orderStub.createMonthlyList(100000L));
        // when
        List<OrderDBMonthlyPriceDto> monthlyPrice = orderService.getMonthlyPrice();
        // then
        Assertions.assertThat(monthlyPrice.stream().map(OrderDBMonthlyPriceDto::getMonth))
                .contains(Month.JANUARY.getValue(), Month.FEBRUARY.getValue(),
                        Month.MARCH.getValue(), Month.APRIL.getValue(), Month.MAY.getValue(),
                        Month.JUNE.getValue());
    }

    @Test
    @DisplayName("일별 상품 조회")
    void get_daily_price_test() throws Exception {
        // given
        given(orderRepository.dailyPrice()).willReturn(orderStub.createDailyList(100000L));
        // when
        List<OrderDBDailyPriceDto> result = orderService.getDailyPrice();
        // then
        Assertions.assertThat(result.stream().map(OrderDBDailyPriceDto::getDay))
                .contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
        Assertions.assertThat(result.stream().map(OrderDBDailyPriceDto::getTotalDiscountedPrice))
                .containsOnly(100000L);
    }

    @Test
    @DisplayName("일별 상품 조회")
    void get_item_price_test() throws Exception {
        // given
        given(orderRepository.itemSales()).willReturn(orderStub.createItemPriceList(100000L));
        // when
        List<OrderDBItemSaleDto> result = orderService.getItemPrice();
        // then
        for (int i = 0; i < 19; i++) {
            Assertions.assertThat(result.get(i).getItemId()).isEqualTo((long) i + 1);
        }
        Assertions.assertThat(result.stream().map(OrderDBItemSaleDto::getItemPrice))
                .containsOnly(100000L);
    }

    @Test
    @DisplayName("일별 상품 조회 : 조회 상품 없음")
    void get_item_price_null_test() throws Exception {
        // given
        given(orderRepository.itemSales()).willReturn(new ArrayList<>());
        // when
        List<OrderDBItemSaleDto> result = orderService.getItemPrice();
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("월별 상품 조회 : 조회 결과 없음")
    void get_month_price_null_test() throws Exception {
        // given
        given(orderRepository.monthlyPrice()).willReturn(new ArrayList<>());
        // when
        List<OrderDBMonthlyPriceDto> result = orderService.getMonthlyPrice();
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("일별 상품 조회 : 조회 결과 없음")
    void get_daily_price_null_test() throws Exception {
        // given
        given(orderRepository.dailyPrice()).willReturn(new ArrayList<>());
        // when
        List<OrderDBDailyPriceDto> result = orderService.getDailyPrice();
        // then
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("주문 취소 테스트")
    void order_cancel_test() throws Exception{
        // given
        given(userService.validUser(anyLong())).willReturn(userStub.createUser(1L));
        given(orderRepository.findAllByOrderId(anyLong())).willReturn(Optional.of(orderStub.createOrder(1L)));

        // when
        orderService.cancelOrder(1L,1L);
        // then
        verify(userService, times(1)).validUser(1L);
        verify(orderRepository, times(1)).findAllByOrderId(1L);
    }

    @Test
    @DisplayName("주문 취소 테스트 : 실패[배송완료]")
    void order_cancel_fail_test() throws Exception{
        // given
        given(userService.validUser(anyLong())).willReturn(userStub.createUser(1L));
        Orders orderWithTrackNum = orderStub.createOrderWithTrackNum(1L);
        orderWithTrackNum.setOrdersStatus(DELIVERY_COMPLETE);
        given(orderRepository.findAllByOrderId(anyLong())).willReturn(Optional.of(orderWithTrackNum));

        // when
        Assertions.assertThatThrownBy(() -> orderService.cancelOrder(1L,1L)).isInstanceOf(BusinessLogicException.class).hasMessage(OrderExceptionCode.ORDER_CANCEL_FAIL.getMessage());
    }

    @Test
    @DisplayName("주문 취소 테스트 : 실패[이미 취소한 상품]")
    void order_cancel_fail_already_cancel_test() throws Exception{
        // given
        given(userService.validUser(anyLong())).willReturn(userStub.createUser(1L));
        Orders orderWithTrackNum = orderStub.createOrderWithTrackNum(1L);
        orderWithTrackNum.setOrdersStatus(ORDER_CANCEL);
        given(orderRepository.findAllByOrderId(anyLong())).willReturn(Optional.of(orderWithTrackNum));

        // when
        Assertions.assertThatThrownBy(() -> orderService.cancelOrder(1L,1L)).isInstanceOf(BusinessLogicException.class).hasMessage(OrderExceptionCode.ORDER_ALREADY_CANCEL.getMessage());
    }

    @Test
    @DisplayName("주문 취소 테스트 : 실패[이미 취소한 상품]")
    void order_cancel_fail_user_access_fail_test() throws Exception{
        // given
        given(userService.validUser(anyLong())).willReturn(userStub.createUser(2L));
        Orders orderWithTrackNum = orderStub.createOrderWithTrackNum(1L);
        given(orderRepository.findAllByOrderId(anyLong())).willReturn(Optional.of(orderWithTrackNum));

        // when
        Assertions.assertThatThrownBy(() -> orderService.cancelOrder(1L,1L)).isInstanceOf(BusinessLogicException.class).hasMessage(UserExceptionCode.USER_NOT_ACCESS.getMessage());
    }

    @Test
    @DisplayName("주문 완료 변경 테스트")
    void order_status_change_complete_test() throws Exception{
        // given
        Orders mockEntity = orderStub.createOrder(1L);

        given(orderRepository.findById(anyLong())).willReturn(Optional.of(mockEntity));
        // when
        Orders result = orderService.orderStatusComplete(1L);

        Assertions.assertThat(result.getOrdersStatus()).isEqualTo(DELIVERY_COMPLETE);
    }

    @Test
    @DisplayName("주문 완료 변경 테스트")
    void order_status_change_complete_fail_test() throws Exception{
        // given

        given(orderRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        Assertions.assertThatThrownBy(() -> orderService.orderStatusComplete(1L)).isInstanceOf(BusinessLogicException.class).hasMessage(OrderExceptionCode.ORDER_NOT_FOUND.getMessage());
    }

}