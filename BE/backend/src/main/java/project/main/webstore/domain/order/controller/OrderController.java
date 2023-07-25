package project.main.webstore.domain.order.controller;


import com.querydsl.core.types.Order;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.order.dto.OrderLocalDto;
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

@Tag(name = "주문 API", description = "주문 관련 API")
@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @ApiResponse(responseCode = "200", description = "주문서 작성, 주문한 아이템 정보 등록")
    @PostMapping("/{user-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> postOrder(@PathVariable("user-id") @Positive Long userId,
                                      @RequestBody @Valid OrderPostDto post,
                                      @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal,userId);
        Orders order = orderMapper.orderPostDtoToOrder(postDto);
        Orders writeOrder = orderService.writeOrder(order,userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(writeOrder);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @ApiResponse(responseCode = "200", description = "주문서 수정")
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
    @ApiResponse(responseCode = "200", description = "주문정보 가져오기")
    @GetMapping("/{order-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(
                                   @PathVariable("order-id") @Positive Long orderId) {
        Orders order = orderService.getOrder(orderId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(order);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    // Get List 필요 확인

    @ApiResponse(responseCode = "204", description = "주문 취소")
    @DeleteMapping("/{user-id}/cancel/{order-id}") // -> order취소..
    public ResponseEntity deleteOrder(@PathVariable("user-id") @Positive Long userId,
                                       @PathVariable("order-idr") @Positive Long orderId,
                                      @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);

        orderService.cancelOrder(userId, orderId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}


