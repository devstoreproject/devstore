package project.main.webstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.repository.OrderRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.repository.UserRepository;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;

    // 현재 user, item domain 연결
    // 주문서 폼 작성, 해당 정보 저장
    // 주문번호 포함 order 생성
    public Orders writeOrder(Orders order, Long userId) {
        User findUser = userService.getUser(userId);
        String orderNumber = CreateOrderNumber();
        order.setUser(findUser);
        order.setOrderNumber(orderNumber);

        return orderRepository.save(order);
    }

    // 주문 양식만 수정
    public Orders editOrder(Orders order, Long userId) {
        User user = userService.getUser(userId);
        Orders patchOrder = findVerifiedOrder(order.getOrderId());
        patchOrder.setUser(user);

        return orderRepository.save(patchOrder);
    }

    public Orders getOrder(long orderId) {
        return findVerifiedOrder(orderId);
    }

//    public List<Orders> getOrders(Long orderId) {
//        return orderRepository.findByAllOrderId(orderId);
//    }

    public void cancelOrder(String orderNumber, Long userId) {
        User findUser = userService.getUser(userId);
        Orders findOrder = findByOrderNumber(orderNumber);

        if (findOrder.getOrdersStatus() == OrdersStatus.ORDER_COMPLETE) {
            findOrder.setOrdersStatus(OrdersStatus.ORDER_CANCEL);

            List<OrderItem> orderItems = findOrder.getOrderItems();

            for (OrderItem orderItem: orderItems) {
                Item item = orderItem.getItem();
                int orderedCount = orderItem.getItemCount();
                int currentCount = orderItem.getItemCount();
                int totalCount = orderedCount + currentCount;
                item.setItemCount(totalCount);
            }
        } else if (findOrder.getOrdersStatus() == OrdersStatus.ORDER_CANCEL) {
            throw new BusinessLogicException(CommonExceptionCode.ORDER_ALREADY_CANCEL);
        } else {
            throw new BusinessLogicException(CommonExceptionCode.ORDER_CANCEL_FAIL);
        }

        findOrder.setUser(findUser);

        orderRepository.save(findOrder);
    }

    public Orders findVerifiedOrder(long orderId) {
        Optional<Orders> findByOrderId = orderRepository.findById(orderId);
        return findByOrderId.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.ORDER_NOT_FOUND));
    }

    // 주문번호 조회
    private Orders findByOrderNumber(String orderNumber) {
        Optional<Orders> findByOrderNum = orderRepository.findByOrderNumber(orderNumber);
        return findByOrderNum.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.ORDER_NOT_FOUND));
    }

    // 주문번호 생성
    public String CreateOrderNumber() {
        Calendar cal = Calendar.getInstance();

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);

        StringBuilder builder = new StringBuilder();
        builder.append(y).append(m).append(d);

        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        return builder.toString();
    }

    // 주문 상태 변경..

}

