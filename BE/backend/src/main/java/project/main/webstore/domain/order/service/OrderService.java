package project.main.webstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.order.repository.OrderRepository;
import project.main.webstore.domain.orderHistory.enums.TransCondition;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserValidService userService;

    public Orders createOrder(OrderLocalDto post, Long userId) {
        User user = userService.validUserAllInfo(userId);
        ShippingInfo shippingInfo = user.getShippingInfo(post.getShippingId());

        Cart cart = user.getCart();
        //장바구니 비우기
        user.setCart(null);
        //TODO : 쿠폰 구현 이후 변경 필요
        Orders order = new Orders(post.getMessage(), cart, user, shippingInfo, new ArrayList<>(), null);
        order.transItemCount(TransCondition.MINUS);
        return orderRepository.save(order);
    }

    // 주문 양식만 수정 요청 사항만 수정이 가능하다.
    public Orders editOrder(OrderLocalDto order, Long userId) {
        Orders findOrder = validOrderAllInfo(order.getOrderId());
        validOrderUserSame(userId, findOrder);
        Optional.ofNullable(order.getMessage()).ifPresent(findOrder::setMessage);
        return findOrder;
    }

    public Orders getOrder(Long orderId, Long userId) {
        //TODO: 본인 체크 필수 혹은 관리자
        User user = userService.validUser(userId);
        Orders order = validOrder(orderId);
        order.setUser(user);

        return order;
    }

    public Orders getOrder(String orderNumber, Long userId) {
        //TODO: 본인 체크 필수 혹은 관리자
        User findUser = userService.validUser(userId);
        Orders findOrder = validOrderByOrderNumberAllInfo(orderNumber);
        findOrder.getUser().validUserHasAccess(findUser);

        return findOrder;
    }

    public Page<Orders> getOrders(Pageable pageable, Long userId) {
        if (userId.equals(-1L)) {
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_LOGIN);
        } else if (userId.equals(0L)) {
            return orderRepository.findAll(pageable);
        } else {
            return orderRepository.findAllByUserId(pageable, userId);
        }
    }

    //주문 취소
    public void cancelOrder(Long orderId, Long userId) {
        User findUser = userService.validUser(userId);

        Orders findOrder = validOrderAllInfo(orderId);
        //order 주문자 여부 체크 -> 실 주문자 or  관리자
        findOrder.getUser().validUserHasAccess(findUser);

        //변경할 수 있는 상태라면
        if (checkStatus(findOrder)) {
            findOrder.setOrdersStatus(OrdersStatus.ORDER_CANCEL);
            findOrder.transItemCount(TransCondition.PLUS);
        } else if (findOrder.getOrdersStatus() == OrdersStatus.ORDER_CANCEL) {
            throw new BusinessLogicException(OrderExceptionCode.ORDER_ALREADY_CANCEL);
        } else {
            throw new BusinessLogicException(OrderExceptionCode.ORDER_CANCEL_FAIL);
        }
    }

    //검증
    public Orders validOrder(Long orderId) {
        Optional<Orders> findOrderId = orderRepository.findById(orderId);
        return findOrderId.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.ORDER_NOT_FOUND));
    }

    public Orders validOrderAllInfo(Long orderId) {
        Optional<Orders> findOrderId = orderRepository.findAllByOrderId(orderId);
        return findOrderId.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.ORDER_NOT_FOUND));
    }

    // 주문번호 조회
    private Orders validOrderByOrderNumber(String orderNumber) {
        Optional<Orders> findByOrderNum = orderRepository.findByOrderNumber(orderNumber);
        return findByOrderNum.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.ORDER_NOT_FOUND));
    }

    private Orders validOrderByOrderNumberAllInfo(String orderNumber) {
        Optional<Orders> findByOrderNum = orderRepository.findAllByOrderNumber(orderNumber);
        return findByOrderNum.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.ORDER_NOT_FOUND));
    }

    // 주문 정보 검증
    private boolean checkStatus(Orders order) {
        return order.getOrdersStatus().getIndex() >= OrdersStatus.ORDER_COMPLETE.getIndex();
    }

    private void validOrderUserSame(Long userId, Orders findOrder) {
        if (!findOrder.getUser().getId().equals(userId)) {
            throw new BusinessLogicException(UserExceptionCode.USER_INFO_MISMATCH);
        }
    }

}

