package project.main.webstore.domain.order.controller;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import project.main.webstore.domain.order.dto.OrderDBDailyPriceDto;
import project.main.webstore.domain.order.dto.OrderDBItemSaleDto;
import project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto;
import project.main.webstore.domain.order.dto.OrderDailyPriceDto;
import project.main.webstore.domain.order.dto.OrderIdAndStatusDto;
import project.main.webstore.domain.order.dto.OrderIdResponseDto;
import project.main.webstore.domain.order.dto.OrderItemSaleDto;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.dto.OrderMonthlyPriceDto;
import project.main.webstore.domain.order.dto.OrderPatchDto;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.dto.OrderRefundRequestDto;
import project.main.webstore.domain.order.dto.OrderResponseDto;
import project.main.webstore.domain.order.dto.OrderTrackingInfoDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.mapper.OrderMapper;
import project.main.webstore.domain.order.service.OrderService;
import project.main.webstore.dto.CustomPage;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;
import project.main.webstore.utils.UriCreator;

@Tag(name = "주문 API", description = "주문 관련 API")
@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final static String ORDER_URL = "orders";
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @ApiResponse(responseCode = "201", description = "주문서 작성, 주문한 아이템 정보 등록")
    @PostMapping
    public ResponseEntity<ResponseDto<OrderIdResponseDto>> postOrder(@RequestBody @Valid OrderPostDto post,
                                                                     @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);
        post.setUserId(userId);
        OrderLocalDto localOrder = orderMapper.orderPostDtoToOrder(post);
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
                                                                    @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdx(principal);

        OrderLocalDto order = orderMapper.orderPatchDtoToOrder(patchDto, orderId);
        Orders updateOrder = orderService.editOrder(order, userId);
        OrderIdResponseDto response = orderMapper.toIdResponse(updateOrder);

        var responseDto = ResponseDto.<OrderIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "주문정보 가져오기")
    @GetMapping("/{order-id}")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrder(@PathVariable("order-id") @Positive Long orderId,
                                                                  @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {

        Long userId = CheckLoginUser.getContextIdx(principal);
        Orders order = orderService.getOrder(orderId, userId);
        OrderResponseDto response = orderMapper.toResponseDto(order);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @ApiResponse(responseCode = "200", description = "주문정보 가져오기")
    @GetMapping("/orderNumber")
    public ResponseEntity<ResponseDto<OrderResponseDto>> getOrderByOrderNumber(@RequestParam String orderNumber,
                                                                               @Parameter(hidden = true)@AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdAdminZero(principal);

        Orders order = orderService.getOrder(orderNumber,userId);
        OrderResponseDto response = orderMapper.toResponseDto(order);

        var responseDto = ResponseDto.<OrderResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "전체 주문정보 가져오기 페이지")
    @GetMapping
    public ResponseEntity<ResponseDto<CustomPage<OrderResponseDto>>> getOrders(@Parameter(hidden = true) @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
                                                                         @RequestParam(value = "month",required = false) Integer month,
                                                                         @Parameter(hidden = true) @AuthenticationPrincipal Object principal) {
        Long userId = CheckLoginUser.getContextIdAdminZero(principal);
        Page<Orders> ordersPage = orderService.getOrders(pageable,userId,month);
        CustomPage<OrderResponseDto> response = orderMapper.toResponsePageDto(ordersPage);

        var responseDto = ResponseDto.<CustomPage<OrderResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();

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
    public ResponseEntity<ResponseDto<List<OrderMonthlyPriceDto>> > getMonthlyAmount(){
        List<OrderDBMonthlyPriceDto> result = orderService.getMonthlyPrice();
        List<OrderMonthlyPriceDto> response = orderMapper.toMonthlyAmountResponse(result);
        var responseDto = ResponseDto.<List<OrderMonthlyPriceDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/day-sale")
    public ResponseEntity<ResponseDto<List<OrderDailyPriceDto>> > getDailyAmount(){
        List<OrderDBDailyPriceDto> result = orderService.getDailyPrice();
        List<OrderDailyPriceDto> response = orderMapper.toDailyAmountResponse(result);
        var responseDto = ResponseDto.<List<OrderDailyPriceDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/items-sale")
    public ResponseEntity<ResponseDto<List<OrderItemSaleDto>>> getItemPrice(){
        List<OrderDBItemSaleDto> result = orderService.getItemPrice();
        List<OrderItemSaleDto> response = orderMapper.toItemSaleResponse(result);
        var responseDto = ResponseDto.<List<OrderItemSaleDto>>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{orderId}/add-tracking-number")
    public ResponseEntity<ResponseDto<OrderIdResponseDto>> setTrackingNumber(@PathVariable @Positive Long orderId, @RequestBody OrderTrackingInfoDto trackingInfo){
        Orders result = orderService.setTrackingNumber(orderId, trackingInfo.getTrackingNumber(),trackingInfo.getDeliveryCompany());
        OrderIdResponseDto response = orderMapper.toIdResponse(result);
        var responseDto = ResponseDto.<OrderIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();
        URI uri = UriCreator.createUri(ORDER_URL + "/{orderId}", responseDto.getData().getOrderId());

        return ResponseEntity.ok().header("Location",uri.toString()).body(responseDto);
    }
    @PatchMapping("/{orderId}/refund")
    public ResponseEntity<ResponseDto<OrderIdAndStatusDto>> refundOrder(@PathVariable Long orderId,
                                                                        @RequestBody OrderRefundRequestDto dto,
                                                                        @Parameter(hidden = true)@AuthenticationPrincipal Object principal){
        Long userId = CheckLoginUser.getContextIdx(principal);
        Orders result = orderService.refundOrder(userId, orderId,dto.getItemIdList());
        OrderIdAndStatusDto response = orderMapper.toResponse(result);
        var responseDto = ResponseDto.<OrderIdAndStatusDto>builder().customCode(ResponseCode.OK).data(response).build();
        URI uri = UriCreator.createUri(ORDER_URL + "/{orderId}", responseDto.getData().getOrderId());

        return ResponseEntity.ok().header("Location",uri.toString()).body(responseDto);
    }
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<ResponseDto<OrderIdResponseDto>> deliveryCompleteOrder(@PathVariable Long orderId,@RequestParam("status") String status){
        Orders result = orderService.changStatus(orderId, status);
        OrderIdResponseDto response = orderMapper.toIdResponse(result);
        var responseDto = ResponseDto.<OrderIdResponseDto>builder().customCode(ResponseCode.OK).data(response).build();

        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseDto<CustomPage<OrderResponseDto>>> getOrderByStatus(Pageable pageable,@RequestParam("status") String status){
        Page<Orders> result = orderService.getOrderByStatus(pageable, status);
        CustomPage<OrderResponseDto> response = orderMapper.toResponsePageDto(result);
        var responseDto = ResponseDto.<CustomPage<OrderResponseDto>>builder().customCode(ResponseCode.OK).data(response).build();

        return ResponseEntity.ok().body(responseDto);
    }

}
