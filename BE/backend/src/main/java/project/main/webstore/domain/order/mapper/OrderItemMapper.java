package project.main.webstore.domain.order.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.order.dto.OrderItemPostDto;
import project.main.webstore.domain.order.dto.OrderItemResponseDto;
import project.main.webstore.domain.order.entity.OrderItem;

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


    public OrderItemResponseDto orderItemToOrderItem(OrderItem orderItem) {
        return OrderItemResponseDto.builder()
                .itemId(orderItem.getItem().getItemId())
                .itemName(orderItem.getItem().getItemName())
                .itemCount(orderItem.getItem().getItemCount())
                .itemPrice(orderItem.getItem().getItemPrice().getValue())
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

    public List<OrderItemResponseDto> orderItemListToOrderItemResponse(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(orders -> orderItemToOrderItem(orders)).collect(Collectors.toList());
    }
}
