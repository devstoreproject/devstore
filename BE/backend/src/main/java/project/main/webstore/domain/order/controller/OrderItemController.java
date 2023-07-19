package project.main.webstore.domain.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.order.dto.OrderItemPostDto;
import project.main.webstore.domain.order.dto.OrderItemResponseDto;
import project.main.webstore.domain.order.dto.OrderResponseDto;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.mapper.OrderItemMapper;
import project.main.webstore.domain.order.service.OrderItemService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderItemController {
    public OrderItemService orderItemService;
    public OrderItemMapper orderItemMapper;

    @PostMapping("/{order-id}/orderItems")
    public ResponseEntity<ResponseDto<OrderItemResponseDto>> postOrderItem(@PathVariable @Positive Long orderId,
                                                                       @RequestBody @Valid OrderItemPostDto postDto) {
        OrderItem orderItem = orderItemMapper.orderItemPostDtoToOrderItem(postDto);
        OrderItem writeOrderItem = orderItemService.writeOrderItem(orderItem, orderId);
        OrderItemResponseDto response = orderItemMapper.orderItemToOrderItem(writeOrderItem);

        var responseDto = ResponseDto.<OrderItemResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    // 단일
    @GetMapping("/{order-id}/orderItems/{order-item-id}")
    public ResponseEntity<ResponseDto<OrderItemResponseDto>> postOrderItem(@PathVariable("order-id") @Positive Long orderId,
                                        @PathVariable("order-item-id") @Positive Long orderItemId,
                                        @RequestBody @Valid OrderItemPostDto postDto) {
        OrderItem orderItem = orderItemService.getOrderItem(orderItemId);
        OrderItemResponseDto response = orderItemMapper.orderItemToOrderItem(orderItem);

        var responseDto = ResponseDto.<OrderItemResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    // 리스트
    @GetMapping("/{order-id}/orderItems")
    public ResponseEntity<ResponseDto<List<OrderItemResponseDto>>> getOrderItemList(@PathVariable("order-item-id") @Positive Long orderItemId) {
        List<OrderItem> orderItemList = orderItemService.getOrderItems(orderItemId);
        List<OrderItemResponseDto> response = orderItemMapper.orderItemListToResponse(orderItemList);

        var responseDto = ResponseDto.<List<OrderItemResponseDto>>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }


    @DeleteMapping("{user-id}/orderItem/{order-item-id}")
    public ResponseEntity deleteOrderItem(@PathVariable("user-id") @Positive Long userId,
                                          @PathVariable("order-item-id") @Positive Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
