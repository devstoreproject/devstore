package project.main.webstore.domain.orderHistory.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.order.service.OrderService;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;
import project.main.webstore.domain.orderHistory.repository.OrderHistoryRepository;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderHistoryService {
    private final OrderHistoryRepository historyRepository;
    private final OrderService orderService;
    private final UserValidService userService;

    public OrderHistory getHistory(Long id, Long orderId, Long userId) {
        Orders order = orderService.getOrder(orderId, userId);
        OrderHistory history = findVerifiedHisory(id);
        history.setOrder(order);
        return history;
    }

    public Page<OrderHistory> getHistory(Pageable pageable) {
        Page<OrderHistory> historyPage = historyRepository.findAll(pageable);

        return historyPage;
    }

    private OrderHistory findVerifiedHisory(Long id) {
        Optional<OrderHistory> findId = historyRepository.findById(id);
        return findId.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.ORDER_NOT_FOUND));
    }
}
