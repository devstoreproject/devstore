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
import project.main.webstore.utils.UriCreator;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@Tag(name = "주문 API", description = "주문 관련 API")
@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final String ORDER_URL = "/api/orders";
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @ApiResponse(responseCode = "201", description = "주문서 작성, 주문한 아이템 정보 등록")
    @PostMapping
    public ResponseEntity<ResponseDto<OrderIdResponseDto>> postOrder(@RequestBody @Valid OrderPostDto post,
                                                                       @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        OrderLocalDto localOrder = orderMapper.orderPostDtoToOrder(post);
        localOrder.addUserId(userId);
        Orders createOrder = orderService.createOrder(localOrder);
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
    public ResponseEntity<ResponseDto<Page<OrderResponseDto>>> getOrders(@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,@RequestParam(value = "month",required = false) Integer month,
                                                                         @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdAdminZero(principal);
        Page<Orders> ordersPage = orderService.getOrders(pageable,userId,month);
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

    //Item 별 주문 내역 확인

    //Item 별 매출

    @GetMapping("/month-sale")
    public ResponseEntity<ResponseDto<List<OrderMonthlyPriceDto>> > getMonthlyAmount(@AuthenticationPrincipal Object principal){
//        CheckLoginUser.validAdmin(principal);
        List<OrderDBMonthlyPriceDto> result = orderService.getMonthlyPrice();
        List<OrderMonthlyPriceDto> response = orderMapper.toMonthlyAmountResponse(result);
        var responseDto = ResponseDto.<List<OrderMonthlyPriceDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/items-sale")
    public ResponseEntity<ResponseDto<List<OrderItemSaleDto>>> getItemPrice(@AuthenticationPrincipal Object principal){
//        CheckLoginUser.validAdmin(principal);
        List<OrderDBItemSaleDto> result = orderService.getItemPrice();
        List<OrderItemSaleDto> response = orderMapper.toItemSaleResponse(result);
        var responseDto = ResponseDto.<List<OrderItemSaleDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("{orderId}/add-tracking-number")
    public ResponseEntity<ResponseDto<OrderIdResponseDto>> setTrackingNumber(@PathVariable @Positive Long orderId, @RequestBody OrderTrackingInfoDto trackingInfo){
        Orders result = orderService.setTrackingNumber(orderId, trackingInfo.getTrackingNumber(),trackingInfo.getDeliveryCompany());
        OrderIdResponseDto response = orderMapper.toIdResponse(result);
        var responseDto = ResponseDto.<OrderIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();
        URI uri = UriCreator.createUri(ORDER_URL + "/{orderId}", responseDto.getData().getOrderId());

        return ResponseEntity.ok().header("Location",uri.toString()).body(responseDto);
    }
    
}
