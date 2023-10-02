package project.main.webstore.domain.order.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.DefaultMapper;
import project.main.webstore.domain.order.dto.OrderDBDailyPriceDto;
import project.main.webstore.domain.order.dto.OrderDBItemSaleDto;
import project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto;
import project.main.webstore.domain.order.dto.OrderDailyPriceDto;
import project.main.webstore.domain.order.dto.OrderIdAndStatusDto;
import project.main.webstore.domain.order.dto.OrderIdResponseDto;
import project.main.webstore.domain.order.dto.OrderItemResponseDto;
import project.main.webstore.domain.order.dto.OrderItemSaleDto;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.dto.OrderMonthlyPriceDto;
import project.main.webstore.domain.order.dto.OrderPatchDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.dto.OrderResponseDto;
import project.main.webstore.domain.order.entity.OrderedItem;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.users.dto.ShippingAddressInfoDto;
import project.main.webstore.domain.users.dto.UserOrderDto;
import project.main.webstore.dto.CustomPage;

@Component
public class OrderMapper implements DefaultMapper {

    public OrderLocalDto orderPostDtoToOrder(OrderPostDto post) {
        return OrderLocalDto.builder()
                .userId(post.getUserId())
                .message(post.getMessage())
                .shippingId(post.getShippingInfoId())
                .orderCartItemIdList(post.getCartItemIdList())
                .build();
    }

    public OrderLocalDto orderPatchDtoToOrder(OrderPatchDto patchDto, Long orderId) {
        return OrderLocalDto.builder()
                .shippingId(patchDto.getShippingInfoId())
                .orderId(orderId)
                .message(patchDto.getMessage())
                .build();
    }

    public OrderResponseDto toResponseDto(Orders order) {
        return OrderResponseDto.builder()
                .orderId(order.getOrderId())
                .orderNumber(order.getOrderNumber())
                .addressInfo(new ShippingAddressInfoDto(order.getAddress(), order.getRecipient()))
                .userInfo(new UserOrderDto(order.getUser()))
                .orderItemList(getOrderItemList(order))
                .totalPrice(order.getTotalOrderedOriginalPrice())
                .discountedPrice(order.getTotalOrderedDiscountedPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .ordersStatus(order.getOrdersStatus())
                .message(order.getMessage())
                .createdAt(order.getCreatedAt())
                .modifiedAt(order.getModifiedAt())
                .build();
    }

    private List<OrderItemResponseDto> getOrderItemList(Orders order) {
        return order.getOrderedItemList() != null ? order.getOrderedItemList().stream()
                .map(this::getOrderItemResponseDto).collect(Collectors.toList())
                : new ArrayList<>();
    }

    private OrderItemResponseDto getOrderItemResponseDto(OrderedItem orderedItem) {
        return OrderItemResponseDto.builder()
                .itemId(orderedItem.getOption().getItem().getItemId())
                .itemName(orderedItem.getOption().getItem().getItemName())
                .itemCount(orderedItem.getItemCount())
                .itemPrice(orderedItem.getPrice())
                .discountRate(orderedItem.getOption().getItem().getDiscountRate())
                .discountPrice(orderedItem.getDiscountedPrice())
                .build();
    }

    public CustomPage<OrderResponseDto> toResponsePageDto(Page<Orders> orderPage) {
        if (orderPage == null) {
            return null;
        }
        return transCustomPage(orderPage.map(this::toResponseDto));
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
        return new OrderIdAndStatusDto(result.getOrderId(), result.getOrdersStatus());
    }

    public List<OrderDailyPriceDto> toDailyAmountResponse(List<OrderDBDailyPriceDto> result) {
        return result.stream().map(OrderDailyPriceDto::new).collect(Collectors.toList());
    }
}