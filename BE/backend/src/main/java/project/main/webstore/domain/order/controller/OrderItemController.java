package project.main.webstore.domain.order.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "주문 아이템 API", description = "주문 아이템 관련 API")
@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderItemController {
    public OrderItemService orderItemService;
    public OrderItemMapper orderItemMapper;

    @ApiResponse(responseCode = "200", description = "주문 아이템 정보 등록")
    @PostMapping("/{order-id}/orderItems")
    public ResponseEntity<ResponseDto<OrderItemResponseDto>> postOrderItem(@PathVariable @Positive Long orderId,
                                                                       @RequestBody @Valid OrderItemPostDto postDto) {
        OrderItem orderItem = orderItemMapper.orderItemPostDtoToOrderItem(postDto);
        OrderItem writeOrderItem = orderItemService.writeOrderItem(orderItem, orderId);
        OrderItemResponseDto response = orderItemMapper.orderItemToOrderItem(writeOrderItem);

        var responseDto = ResponseDto.<OrderItemResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "주문 아이템 단일 조회")
    @GetMapping("/{order-id}/orderItems/{order-item-id}")
    public ResponseEntity<ResponseDto<OrderItemResponseDto>> postOrderItem(@PathVariable("order-id") @Positive Long orderId,
                                        @PathVariable("order-item-id") @Positive Long orderItemId,
                                        @RequestBody @Valid OrderItemPostDto postDto) {
        OrderItem orderItem = orderItemService.getOrderItem(orderItemId);
        OrderItemResponseDto response = orderItemMapper.orderItemToOrderItem(orderItem);

        var responseDto = ResponseDto.<OrderItemResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "주문 아이템 목록 전체 조회 ")
    @GetMapping("/{order-id}/orderItems")
    public ResponseEntity<ResponseDto<List<OrderItemResponseDto>>> getOrderItemList(@PathVariable("order-item-id") @Positive Long orderItemId) {
        List<OrderItem> orderItemList = orderItemService.getOrderItems(orderItemId);
        List<OrderItemResponseDto> response = orderItemMapper.orderItemListToResponse(orderItemList);

        var responseDto = ResponseDto.<List<OrderItemResponseDto>>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "204", description = "주문한 아이템 삭제")
    @DeleteMapping("{user-id}/orderItem/{order-item-id}")
    public ResponseEntity deleteOrderItem(@PathVariable("user-id") @Positive Long userId,
                                          @PathVariable("order-item-id") @Positive Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId, userId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}