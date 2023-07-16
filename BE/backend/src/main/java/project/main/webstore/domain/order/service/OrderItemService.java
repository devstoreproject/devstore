package project.main.webstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.repository.OrderItemRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderService orderService;
    private final UserService userService;

    public OrderItem writeOrderItem(OrderItem orderItem, Long orderId) {
        Orders findOrder = orderService.findVerifiedOrder(orderId);
        orderItem.setItem(findOrder.getItem());
        findOrder.getOrderItems().add(orderItem);

        return orderItemRepository.save(orderItem);
    }

    public OrderItem getOrderItem(Long orderId) {
        return findVerifiedOrderItem(orderId);
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }

    public OrderItem findVerifiedOrderItem(Long orderFormId) {
        Optional<OrderItem> findByOrderITemId = orderItemRepository.findByOrderItemId(orderFormId);
        OrderItem foundOrderItem = findByOrderITemId.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.ORDER_ITEM_NOT_FOUND));

        return foundOrderItem;
    }

    public void deleteOrderItem(Long orderItemId, Long userId) {
        User user = userService.getUser(userId);

        OrderItem findOrderItem = findVerifiedOrderItem(orderItemId);
        Orders order = findOrderItem.getOrder();
        order.getOrderItems().remove(findOrderItem);

        orderItemRepository.delete(findOrderItem);
    }
}

