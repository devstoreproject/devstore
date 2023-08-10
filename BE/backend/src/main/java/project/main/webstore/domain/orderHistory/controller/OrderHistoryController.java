package project.main.webstore.domain.orderHistory.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.main.webstore.domain.order.dto.OrderResponseDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.orderHistory.dto.OrderHistoryResponseDto;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;
import project.main.webstore.domain.orderHistory.mapper.OrderHistoryMapper;
import project.main.webstore.domain.orderHistory.service.OrderHistoryService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;

import javax.validation.constraints.Positive;

@Tag(name = "주문내역 API", description = "주문내역 관련 API")
@RestController
@RequestMapping("/api/history")
@Validated
@RequiredArgsConstructor
public class OrderHistoryController {
    private final OrderHistoryService historyService;
    private final OrderHistoryMapper mapper;

    @ApiResponse(responseCode = "200", description = "주문정보 가져오기")
    @GetMapping("/{user-id}/{order-id}/{id}")
    public ResponseEntity<ResponseDto<OrderHistoryResponseDto>> getHistory(@PathVariable("user-id") @Positive Long userId,
                                                                           @PathVariable("order-id") @Positive Long orderId,
                                                                           @PathVariable("id") @Positive Long id) {

        OrderHistory history = historyService.getHistory(orderId, userId,id);
        OrderHistoryResponseDto response = mapper.historyToHistoryResponseDto(history);

        var responseDto = ResponseDto.<OrderHistoryResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "전체 주문내역 가져오기 페이지")
    @GetMapping
    public ResponseEntity<ResponseDto<Page<OrderHistoryResponseDto>>> getHistories(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderHistory> historyPage = historyService.getHistory(pageable);
        Page<OrderHistoryResponseDto> response = mapper.historyToHistoryDtoPage(historyPage);

        var responseDto = ResponseDto.<Page<OrderHistoryResponseDto>>builder().data(response).customCode(ResponseCode.OK).build();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
