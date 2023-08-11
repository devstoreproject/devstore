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
import project.main.webstore.domain.order.dto.*;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.mapper.OrderMapper;
import project.main.webstore.domain.order.service.OrderService;
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

    @ApiResponse(responseCode = "201", description = "주문서 작성, 주문한 아이템 정보 등록")
    @PostMapping("/users/{user-id}")
    public ResponseEntity<ResponseDto<OrderIdResponseDto>> postOrder(@PathVariable("user-id") @Positive Long userId,
                                                                       @RequestBody @Valid OrderPostDto post,
                                                                       @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        OrderLocalDto localOrder = orderMapper.orderPostDtoToOrder(post);
        Orders createOrder = orderService.createOrder(localOrder, userId);
        OrderIdResponseDto response = orderMapper.toIdResponse(createOrder);

        var responseDto = ResponseDto.<OrderIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = "주문서 수정")
    @PatchMapping("/{order-id}")
    public ResponseEntity<ResponseDto<OrderIdResponseDto>> patchOrder(
                                                                    @PathVariable("order-id") @Positive Long orderId,
                                                                    @RequestBody @Valid OrderPatchDto patchDto,
                                                                    @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);

        OrderLocalDto order = orderMapper.orderPatchDtoToOrder(patchDto, orderId);
        Orders updateOrder = orderService.editOrder(order, userId);
        OrderIdResponseDto response = orderMapper.toIdResponse(updateOrder);

        var responseDto = ResponseDto.<OrderIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "주문정보 가져오기")
    @GetMapping("/{order-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(@PathVariable("order-id") @Positive Long orderId,@AuthenticationPrincipal Object principal) {

        Long userId = CheckLoginUser.getContextIdx(principal);
        Orders order = orderService.getOrder(orderId, userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(order);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @ApiResponse(responseCode = "200", description = "주문정보 가져오기")
    @GetMapping("/orderNumber")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrderByOrderNumber(@RequestParam String orderNumber,
                                                                               @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdAdminZero(principal);

        Orders order = orderService.getOrder(orderNumber,userId);
        OrderResponseDto response = orderMapper.orderToOrderResponseDto(order);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "전체 주문정보 가져오기 페이지")
    @GetMapping
    public ResponseEntity<ResponseDto<Page<OrderResponseDto>>> getOrders(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdAdminZero(principal);
        Page<Orders> ordersPage = orderService.getOrders(pageable,userId);
        Page<OrderResponseDto> response = orderMapper.orderToOrderResponsePage(ordersPage);

        var responseDto = ResponseDto.<Page<OrderResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "204", description = "주문 취소")
    @DeleteMapping("/{order-id}")
    public ResponseEntity deleteOrder(@PathVariable("order-id") @Positive Long orderId,
                                      @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);

        orderService.cancelOrder(userId, orderId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
/*
 * Order 주문 이후 주문 기록 보관소에 데이터가 저장이 되어야한다.
 * 주문완료 이후 주문 기록이 보관소에 저장되어야한다.
 * 주문 상황이 변경될 경우 history에 주문 사항에 대한 수정이 이루어져야한다.
 * 주문 수정 시 주문 내역이 전부 기록된다
 *
 * 기간별 월별 주문ㄴ 매출 알 수 있어야한다.
 * 주문 기록을 통해 매출을 알 수 있어야한다.
 * 주문 내역 기간별 을 알 수 있어야한다.
 * 조회 시 주문 정보를 조회할 수 있는 데이터가 존재해야한다.
 * 상품별 주문 내역을 알 수 있어야한다.
//TODO:주문 완료 시 카트가 비워진다.
//TODO:주문 시 카트에 있는 정보가 전부 이관된다. -> Cart에 있는 데이터 정보들이 전부 Order로 들어오게 된다. -> 이를 위해서는 CartItem은 살아있어야한다. -> 이를 구현하기 위해서 다양한방법이 존재한다.
 * */

