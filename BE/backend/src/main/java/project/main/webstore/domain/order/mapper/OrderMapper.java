package project.main.webstore.domain.order.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.order.dto.*;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {
    public Orders orderFormPostDtoToOrderForm(OrderFormPostDto orderFormPostDto) {
        Orders orderForm = new Orders();
        orderForm.setRecipient(orderFormPostDto.getRecipient());
        orderForm.setEmail(orderFormPostDto.getEmail());
        orderForm.setMobileNumber(orderFormPostDto.getMobileNumber());
        orderForm.setZipCode(orderFormPostDto.getZipCode());
        orderForm.setAddressSimple(orderFormPostDto.getAddressSimple());
        orderForm.setAddressDetail(orderFormPostDto.getAddressDetail());
        orderForm.setMessage(orderFormPostDto.getMessage());

        return orderForm;
    }

    public OrderFormResponseDto orderFormToOrderResponseDto(Orders order) {
        OrderFormResponseDto orderFormResponseDto = new OrderFormResponseDto();
        orderFormResponseDto.setOrderId(order.getOrderId());
        orderFormResponseDto.setRecipient(order.getRecipient());
        orderFormResponseDto.setEmail(order.getEmail());
        orderFormResponseDto.setMobileNumber(order.getMobileNumber());
        orderFormResponseDto.setHomeNumber(order.getHomeNumber());
        orderFormResponseDto.setZipCode(order.getZipCode());
        orderFormResponseDto.setAddressSimple(order.getAddressSimple());
        orderFormResponseDto.setAddressDetail(order.getAddressDetail());
        orderFormResponseDto.setMessage(order.getMessage());
//        orderFormResponseDtoDto.setDeliveryPrice(order.getDeliveryPrice().getValue());
//        orderFormResponseDtoDto.setDiscountPrice(order.getDiscountedPrice().getValue());
//        orderFormResponseDtoDto.setTotalPrice(order.getTotalPrice().getValue());
//        orderFormResponseDtoDto.setOrdersStatus(order.getOrdersStatus());
        orderFormResponseDto.setCreatedAt(order.getCreatedAt());

        return orderFormResponseDto;
    }

     // 작성된 기존의 orderForm을 수정하기위한 orderPatchForm
    public Orders orderFormPatchDtoToOrderForm(OrderFormPatchDto orderFormPatchDto) {
        Orders orderPatchForm = new Orders();
        orderPatchForm.setRecipient(orderFormPatchDto.getRecipient());
        orderPatchForm.setMobileNumber(orderFormPatchDto.getMobileNumber());
        orderPatchForm.setHomeNumber(orderFormPatchDto.getHomeNumber());
        orderPatchForm.setAddressSimple(orderFormPatchDto.getAddressSimple());
        orderPatchForm.setAddressDetail(orderFormPatchDto.getAddressDetail());

        return orderPatchForm;
    }

    // 결제로 넘어갈 order 정보 (item, count, price, discount 정보 포함)
//    public Orders orderPostDtoToOrder(OrderPostDto orderPostDto) {
//        Orders order = new Orders();
////        List<Long> orderCartItem = orderPostDto.getOrderItem().stream()
////                .map(OrderItemPostDto::getCartItemId).collect(Collectors.toList());
////        for (Long cartItemId : orderCartItem) {
////            order.setOrderCartItem(cartItemId);
////
//        List<OrderItemPostDto> orderItemPostDtos = orderPostDto.getOrderItem();
//        for (OrderItemPostDto orderItemList : orderItemPostDtos) {
//            Long cartItemId = orderItemList.getCartItemId();
//            Long itemId    = orderItemList.getItemId();
//            long itemCount = orderItemList.getItemCount();
//            order.setOrderCartItem(cartItemId);
//            order.setOrderCartItem(itemId);
//            order.setOrderCartItem(itemCount);
//        }
//        return order;
//    }

    public List<OrderResponseDto> orderToOrderResponseDto(List<Orders> orderList) {
        List<OrderResponseDto> orderResponseDtos = orderList.stream().map(orders -> {
            OrderResponseDto orderResponseDto = new OrderResponseDto();
            orderResponseDto.setOrderNumber(orders.getOrderNumber());
            orderResponseDto.setRecipient(orders.getUser().getNickName());
            orderResponseDto.setEmail(orders.getUser().getEmail());
            orderResponseDto.setMobileNumber(orders.getMobileNumber());
            orderResponseDto.setHomeNumber(orders.getHomeNumber());
//            orderResponseDto.setZipCode(orders.getUser().getPhone().getZipCode());
//            orderResponseDto.setAddressSimple(orders.getUser().getAddress().getAddressSimple());
//            orderResponseDto.setAddressDetail(orders.getUser().getAddress().getAddressDetail());
            orderResponseDto.setMessage(orders.getMessage());
            orderResponseDto.setTotalPrice(orders.getTotalPrice().getValue());
            return orderResponseDto;
        }).collect(Collectors.toList());
        return orderResponseDtos;
    }

    // OrderItemPostDto


    public List<OrderItemResponseDto> orderItemResponseDto(List<OrderItem> orderItemList) {
        List<OrderItemResponseDto> orderItemResponseDtos = orderItemList.stream().map(orderItem -> {
            OrderItemResponseDto orderItemResponseDto = new OrderItemResponseDto();
//            orderItemResponseDto.setItemName(orderItem.getItem().getName());
//            orderItemResponseDto.setItemPrice(orderItem.getItem().getPrice().getValue());
//            orderItemResponseDto.setItemCount(orderItem.getItem().getItemCount());
//            Dummy Data --------------------------------------------------
            orderItemResponseDto.setItemCount(orderItem.getItemCount());
            orderItemResponseDto.setItemName(orderItem.getItemName());
            orderItemResponseDto.setItemPrice(orderItem.getItemPrice());
            return orderItemResponseDto;
        }).collect(Collectors.toList());
        return orderItemResponseDtos;
    }


    public OrderDummyResponse orderDummyToDummyResponse(Orders order, List<Orders> orderItemList) {
        OrderDummyResponse orderDummyResponse = new OrderDummyResponse();
        orderDummyResponse.setOrderId(order.getOrderId());
        orderDummyResponse.setOrderNumber(order.getOrderNumber());
        orderDummyResponse.setRecipient(order.getRecipient());
        orderDummyResponse.setEmail(order.getEmail());
        orderDummyResponse.setMobileNumber(order.getMobileNumber());
        orderDummyResponse.setHomeNumber(order.getHomeNumber());
        orderDummyResponse.setZipCode(order.getZipCode());
        orderDummyResponse.setAddressSimple(order.getAddressSimple());
        orderDummyResponse.setAddressDetail(order.getAddressDetail());
        orderDummyResponse.setMessage(order.getMessage());


        if (!orderItemList.isEmpty()) {
            for (Orders orderItem : orderItemList) {
                OrderItemPostDto orderItemPostDto = new OrderItemPostDto();
                orderItemPostDto.setItemCount(orderItem.getItemCount());
                orderItemPostDto.setItemName(orderItem.getItemName());
                orderItemPostDto.setItemPrice(orderItem.getItemPrice());
                orderDummyResponse.getOrderItems().add(orderItemPostDto);
//            orderDummyResponse.setItemCount(firstOrderItem.getItemCount());
//            orderDummyResponse.setItemName(firstOrderItem.getItemName());
//            orderDummyResponse.setItemPrice(firstOrderItem.getItemPrice());
            }
        }
        return orderDummyResponse;
    }
}

