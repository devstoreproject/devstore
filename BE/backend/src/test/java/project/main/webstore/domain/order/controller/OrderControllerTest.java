package project.main.webstore.domain.order.controller;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.dto.OrderPatchDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.dto.OrderRefundRequestDto;
import project.main.webstore.domain.order.dto.OrderTrackingInfoDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
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
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT, userId = 1L)
    void post_order_test() throws Exception {
        // given
        OrderPostDto post = orderStub.createOrderPost();
        String content = gson.toJson(post);

        given(orderService.createOrder(any(OrderLocalDto.class)))
                .willReturn(orderStub.createOrder(1L));
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L));
    }

    @Test
    @DisplayName("주문 수정")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT, userId = 1L)
    void order_patch_test() throws Exception {
        // given
        OrderPatchDto patch = new OrderPatchDto("수정할 메시지", 1L);
        String content = gson.toJson(patch);
        given(orderService.editOrder(any(OrderLocalDto.class),
                anyLong())).willReturn(orderStub.createOrder(1L));
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_URL + "/{order-id}", 1L).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L));
    }

    @Test
    @DisplayName("주문정보 조회: id 활용")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT, userId = 1L)
    void get_order_by_id() throws Exception {
        // given
        Orders order = orderStub.createOrder(1L);

        given(orderService.getOrder(anyLong(), anyLong())).willReturn(order);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/{order-id}", 1L));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data.addressInfo.recipient").value("주문자"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("C200"));
    }

    @Test
    @DisplayName("주문정보 조회: order number 활용")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT, userId = 1L)
    void get_order_by_order_number_test() throws Exception {
        // given
        Orders order = orderStub.createOrder(1L);

        given(orderService.getOrder(anyString(), anyLong())).willReturn(order);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/orderNumber")
                        .param("orderNumber", "20231030951408706"));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data.addressInfo.recipient").value("주문자"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("C200"));
    }

    @Test
    @DisplayName("주문정보 페이지 전체 조회")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT, userId = 1L)
    void get_order_page_test() throws Exception {
        // given
        MultiValueMap param = orderStub.getPageParam();

        given(orderService.getOrders(any(Pageable.class), anyLong(), any())).willReturn(
                orderStub.createOrderPage());
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL).params(param));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].orderId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].orderId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].orderId").value(3L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].addressInfo.recipient")
                        .value("주문자"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.offset").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.size").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("C200"));
    }

    @Test
    @DisplayName("월별 매출")
    @WithMockCustomUser
    void month_sale_test() throws Exception {
        given(orderService.getMonthlyPrice()).willReturn(orderStub.createMonthlyList(100000L));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/month-sale"));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].totalDiscountedPrice")
                        .value(100000L));
    }

    @Test
    @DisplayName("일별 매출")
    @WithMockCustomUser
    void daily_sale_test() throws Exception {
        given(orderService.getDailyPrice()).willReturn(orderStub.createDailyList(10000000L));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/day-sale"));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].totalDiscountedPrice")
                        .value(10000000L));
    }

    @Test
    @DisplayName("상품별 매출")
    @WithMockCustomUser
    void item_sale_test() throws Exception {
        given(orderService.getItemPrice()).willReturn(orderStub.createItemPriceList(10000000L));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/items-sale"));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].itemPrice").value(10000000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].discountedPrice")
                        .value(10000000L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].itemId").value(2L));
    }

    @Test
    @DisplayName("배송 번호 추가 테스트")
    @WithMockCustomUser
    void add_tracking_number_test() throws Exception {
        // given
        OrderTrackingInfoDto patch = orderStub.createOrderTrackingInfo();
        String content = gson.toJson(patch);
        given(orderService.setTrackingNumber(anyLong(), anyString(), anyString())).willReturn(
                orderStub.createOrderWithTrackNum(1L));
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_URL + "/{orderId}/add-tracking-number", 1L)
                        .content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("환불 테스트")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT, userId = 1L)
    void refund_test() throws Exception {
        // given
        OrderRefundRequestDto patch = orderStub.createOrderRefundRequestDto();
        String content = gson.toJson(patch);
        Orders orderWithTrackNum = orderStub.createOrderWithTrackNum(1L);
        orderWithTrackNum.setOrdersStatus(OrdersStatus.ORDER_CANCEL);
        given(orderService.refundOrder(anyLong(), anyLong(), anyList())).willReturn(
                orderWithTrackNum);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_URL + "/{orderId}/refund", 1L)
                        .content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("주문 상태 변경 테스트")
    @WithMockCustomUser
    void patch_order_status_test() throws Exception {
        // given
        Orders orderWithTrackNum = orderStub.createOrderWithTrackNum(1L);
        orderWithTrackNum.setOrdersStatus(OrdersStatus.ORDER_COMPLETE);
        given(orderService.changStatus(anyLong(), anyString())).willReturn(
                orderWithTrackNum);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_URL + "/{orderId}/status", 1L)
                        .param("status", "DELIVERY_COMPLETE"));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.orderId").value(1L))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("주문 상태별 조회 테스트")
    @WithMockCustomUser
    void get_order_by_order_status_test() throws Exception {
        // given
        Orders orderWithTrackNum = orderStub.createOrderWithTrackNum(1L);
        orderWithTrackNum.setOrdersStatus(OrdersStatus.ORDER_COMPLETE);
        given(orderService.getOrderByStatus(any(Pageable.class), anyString())).willReturn(
                orderStub.createOrderPage());
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/status")
                        .param("status", "ORDER_COMPLETE"));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].ordersStatus")
                        .value("ORDER_COMPLETE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].ordersStatus")
                        .value("ORDER_COMPLETE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[2].ordersStatus")
                        .value("ORDER_COMPLETE"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("주문 취소 테스트")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT, userId = 1L)
    void delete_order_test() throws Exception{
        willDoNothing().given(orderService).cancelOrder(anyLong(),anyLong());

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.delete(DEFAULT_URL + "/{order-id}",1L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}

