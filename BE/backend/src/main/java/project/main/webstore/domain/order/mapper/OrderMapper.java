package project.main.webstore.domain.order.mapper;

import io.lettuce.core.BitFieldArgs;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.order.dto.*;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

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

//    private List<LocalOrderCountInfo> toTrans(List<OrderItemPostDto> orderItemList) {
//        List<LocalOrderCountInfo> orderItemDtoList = new ArrayList<>();
//        if (orderItemList == null) {
//            return orderItemDtoList;
//        }
//        for (OrderItemPostDto post: orderItemList) {
//            LocalOrderCountInfo countInfo = new LocalOrderCountInfo(post);
//            orderItemDtoList.add(countInfo);
//        }
//        return orderItemDtoList;
//    }
}