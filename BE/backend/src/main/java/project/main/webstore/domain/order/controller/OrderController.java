package project.main.webstore.domain.order.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.dto.OrderPatchDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.dto.OrderResponseDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.mapper.OrderMapper;
import project.main.webstore.domain.order.service.OrderService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;
import retrofit2.http.GET;

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
        CheckLoginUser.validUserSame(principal, userId);
        OrderLocalDto localOrder = orderMapper.orderPostDtoToOrder(post);
        Orders createOrder = orderService.createOrder(localOrder, userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(createOrder);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "주문서 수정")
    @PatchMapping("/{user-id}/{order-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> patchOrder(@PathVariable("user-id") @Positive Long userId,
                                                                    @PathVariable("order-id") @Positive Long orderId,
                                                                    @RequestBody @Valid OrderPatchDto patchDto,
                                                                    @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);

        OrderLocalDto order = orderMapper.orderPatchDtoToOrder(patchDto, orderId);
        Orders updateOrder = orderService.editOrder(order, userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(updateOrder);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "주문정보 가져오기")
    @GetMapping("/{user-id}/{order-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(@PathVariable("user-id") @Positive Long userId,
                                                                  @PathVariable("order-id") @Positive Long orderId) {
        Orders order = orderService.getOrder(orderId, userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(order);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "전체 주문정보 가져오기 페이지")
    @GetMapping
    public ResponseEntity<ResponseDto<Page<OrderResponseDto>>> getOrders(@PageableDefault(sort = "orderId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Orders> ordersPage = orderService.getOrders(pageable);
        Page<OrderResponseDto> response = orderMapper.orderToOrderResponsePage(ordersPage);

        var responseDto = ResponseDto.<Page<OrderResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

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


