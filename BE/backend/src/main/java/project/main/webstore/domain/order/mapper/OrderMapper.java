package project.main.webstore.domain.order.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.order.dto.*;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public OrderMapper(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

    public Orders orderPostDtoToOrder(OrderPostDto postDto) {
        if (postDto == null) return null;
        Orders order = new Orders();
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemPostDto orderItemPostDto: postDto.getOrderItems()) {
            OrderItem orderItem = orderItemMapper.orderItemPostDtoToOrderItem(orderItemPostDto);
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        order.setRecipient(postDto.getRecipient());
        order.setEmail(postDto.getEmail());
        order.setMobileNumber(postDto.getMobileNumber());
        order.setHomeNumber(postDto.getHomeNumber());
        order.setZipCode(postDto.getZipCode());
        order.setAddressSimple(postDto.getAddressSimple());
        order.setAddressDetail(postDto.getAddressDetail());
        order.setMessage(postDto.getMessage());

        return order;
    }

    public Orders orderPatchDtoToOrder(OrderPatchDto patchDto) {
        if (patchDto == null) return null;
        return Orders.builder()
                .recipient(patchDto.getRecipient())
                .email(patchDto.getEmail())
                .mobileNumber(patchDto.getMobileNumber())
                .homeNumber(patchDto.getHomeNumber())
                .zipCode(patchDto.getZipCode())
                .addressSimple(patchDto.getAddressSimple())
                .addressDetail(patchDto.getAddressDetail())
                .message(patchDto.getMessage())
                .build();
    }

    // 주문 정보 전달 (주문서 + 주문건)
    // User 추가
    public OrderResponseDto orderToOrderResponseDto(Orders orders) {
        List<OrderItemResponseDto> orderItemResponseDtoList = orders.getOrderItems().stream().map(orderItem -> {
            OrderItemResponseDto orderItemResponseDto = OrderItemResponseDto.builder()
                    .itemId(orderItem.getItem().getItemId())
                    .itemName(orderItem.getItem().getItemName())
                    .itemCount(orderItem.getItemCount())
                    .itemPrice(orderItem.getItem().getItemPrice().getValue())
                    .build();
            return orderItemResponseDto;
        }).collect(Collectors.toList());

        OrderResponseDto orderResponseDto = OrderResponseDto.builder()
                .orderId(orders.getOrderId())
                .orderNumber(orders.getOrderNumber())
                .recipient(orders.getRecipient())
                .email(orders.getEmail())
                .mobileNumber(orders.getMobileNumber())
                .homeNumber(orders.getHomeNumber())
                .zipCode(orders.getAddress().getZipCode())
                .addressSimple(orders.getAddress().getAddressSimple())
                .addressDetail(orders.getAddress().getAddressDetail())
                .message(orders.getMessage())
                .createdAt(orders.getCreatedAt())
                .modifiedAt(orders.getModifiedAt())
                .orderItemList(orderItemResponseDtoList)
                .totalPrice(orders.getTotalPrice())
                .build();

        return orderResponseDto;
    }

    public List<OrderResponseDto> orderToOrderResponseDtoList(List<Orders> orderList) {
        return orderList.stream().map(orders -> orderToOrderResponseDto(orders)).collect(Collectors.toList());
    }
}