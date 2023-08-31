package project.main.webstore.domain.order.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.order.dto.*;
import project.main.webstore.domain.order.entity.Orders;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public OrderLocalDto orderPostDtoToOrder(OrderPostDto postDto) {
        return new OrderLocalDto(postDto);
    }

    public OrderLocalDto orderPatchDtoToOrder(OrderPatchDto patchDto,Long orderId) {
        return OrderLocalDto.builder()
                .shippingId(patchDto.getShippingInfoId())
                .orderId(orderId)
                .message(patchDto.getMessage())
                .build();
    }

    // 주문 정보 전달 (주문서 + 주문건)
    // User 추가
    public OrderResponseDto orderToOrderResponseDto(Orders orders) {
        return new OrderResponseDto(orders);
    }

    public List<OrderResponseDto> orderToOrderResponseDtoList(List<Orders> orderList) {
        return orderList.stream().map(orders -> orderToOrderResponseDto(orders)).collect(Collectors.toList());
    }

    public Page<OrderResponseDto> orderToOrderResponsePage(Page<Orders> orderPage) {
        return orderPage.map(OrderResponseDto::new);
    }

    public OrderIdResponseDto toIdResponse(Orders order) {
        return new OrderIdResponseDto(order.getOrderId());
    }

    public List<OrderMonthlyPriceDto> toMonthlyAmountResponse(List<OrderDBMonthlyPriceDto> result) {
        return result.stream().map(OrderMonthlyPriceDto::new).collect(Collectors.toList());
    }

    public List<OrderItemSaleDto> toItemSaleResponse(List<OrderDBItemSaleDto> result) {
        return result.stream().map(OrderItemSaleDto::new).collect(Collectors.toList());
    }

    public OrderIdAndStatusDto toResponse(Orders result) {
        return new OrderIdAndStatusDto(result.getOrderId(),result.getOrdersStatus());
    }
}