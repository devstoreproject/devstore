package project.main.webstore.domain.order.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.dto.OrderItemIdResponseDto;
import project.main.webstore.domain.order.dto.OrderItemPostDto;
import project.main.webstore.domain.order.dto.OrderItemResponseDto;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderItemMapper {
    public OrderItem orderItemPostDtoToOrderItem(OrderItemPostDto postDto) {
        if (postDto == null) return null;
        return OrderItem.builder()
                .itemId(postDto.getItem().getItemId())
                .itemCount(postDto.getItem().getItemCount())
                .build();
    }

    public OrderItemResponseDto orderItemToResponse(Orders orderItems) {
        return OrderItemResponseDto.builder()
                .itemId(orderItems.getItem().getItemId())
                .itemName(orderItems.getItem().getItemName())
                .itemCount(orderItems.getItem().getItemCount())
                .itemPrice(orderItems.getItem().getItemPrice().getValue())
                .build();
    }

    public List<OrderItemResponseDto> orderItemListToResponse(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(orderItem -> {
            OrderItemResponseDto orderItemResponseDto = OrderItemResponseDto.builder()
                    .itemId(orderItem.getItem().getItemId())
                    .itemName(orderItem.getItem().getItemName())
                    .itemCount(orderItem.getItem().getItemCount())
                    .itemPrice(orderItem.getItem().getItemPrice().getValue())
                    .build();

            return orderItemResponseDto;
        }).collect(Collectors.toList());
    }

    public List<OrderItemResponseDto> orderItemListToOrderItemResponse(List<Orders> orderList) {
        return orderList.stream().map(orders -> orderItemToResponse(orders)).collect(Collectors.toList());
    }

    public OrderItemIdResponseDto toOrderItemIdResponse(OrderItem orderItem) {
        return new OrderItemIdResponseDto(orderItem.getItem().getItemId(),orderItem.getOrder().getOrderId(),orderItem.getOrderItemId());
    }
}
