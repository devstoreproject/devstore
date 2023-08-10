package project.main.webstore.domain.orderHistory.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.orderHistory.dto.OrderHistoryResponseDto;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderHistoryMapper {
    public OrderHistoryResponseDto historyToHistoryResponseDto(OrderHistory history) {
        return new OrderHistoryResponseDto(history);
    }

    public List<OrderHistoryResponseDto> historyToHistoryResponseDtoList(List<OrderHistory> historyList) {
        return historyList.stream().map(history -> historyToHistoryResponseDto(history)).collect(Collectors.toList());
    }

    public Page<OrderHistoryResponseDto> historyToHistoryDtoPage(Page<OrderHistory> historyPage) {
        return historyPage.map(OrderHistoryResponseDto::new);
    }
}