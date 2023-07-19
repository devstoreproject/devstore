package project.main.webstore.domain.order.controller;


import com.querydsl.core.types.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.order.dto.OrderPatchDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.dto.OrderResponseDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.mapper.OrderItemMapper;
import project.main.webstore.domain.order.mapper.OrderMapper;
import project.main.webstore.domain.order.service.OrderItemService;
import project.main.webstore.domain.order.service.OrderService;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping("{user-id}")
    public ResponseEntity postOrder(@PathVariable("user-id") @Positive Long userId,
                                      @RequestBody @Valid OrderPostDto postDto,
                                      @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal,userId);
        Orders order = orderMapper.orderPostDtoToOrder(postDto);
        Orders writeOrder = orderService.writeOrder(order,userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(writeOrder);

        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{user-id}/edit/{order-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> patchOrder(@PathVariable("user-id") @Positive Long userId,
                                    @PathVariable("order-id") @Positive Long orderId,
                                    @RequestBody @Valid OrderPatchDto patchDto,
                                    @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal,userId);

        Orders order = orderMapper.orderPatchDtoToOrder(patchDto);
        Orders updateOrder = orderService.editOrder(order,userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(updateOrder);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(
                                   @PathVariable("order-id") @Positive Long orderId) {
        Orders order = orderService.getOrder(orderId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(order);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{user-id}/cancel/{order-id}") // -> order취소..
    public ResponseEntity deleteOrder(@PathVariable("user-id") @Positive Long userId,
                                       @PathVariable("order-idr") @Positive Long orderId,
                                      @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);

        orderService.cancelOrder(userId, orderId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}


