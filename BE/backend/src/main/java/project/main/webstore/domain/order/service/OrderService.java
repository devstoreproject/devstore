package project.main.webstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.order.repository.OrderRepository;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserValidService userService;

    public Orders createOrder(OrderLocalDto post, Long userId) {
        User user = userService.validUser(userId);
        String orderNumber = CreateOrderNumber();
        Cart cart = user.getCart();
        ShippingInfo shippingInfo = user.getShippingInfo(post.getShippingId());

        //item 에서 수량 제거하는 로직 추가
        //전체 아이템에서 선택한 아이템 수만큼 수량 제거...
        Orders order = new Orders(orderNumber, post.getMessage(), cart, user, shippingInfo, null, null);

        return orderRepository.save(order);
    }

    // 주문 양식만 수정 요청 사항만 수정이 가능하다.
    public Orders editOrder(OrderLocalDto order, Long userId) {
        User user = userService.validUserAllInfo(userId);
        Orders patchOrder = findVerifiedOrder(order.getOrderId());

        Optional.ofNullable(order.getMessage()).ifPresent(patchOrder::setMessage);

        return orderRepository.save(patchOrder);
    }

    public Orders getOrder(Long orderId, Long userId) {
        //TODO: 본인 체크 필수 혹은 관리자
        User user = userService.validUser(userId);
        Orders order = findVerifiedOrder(orderId);
        order.setUser(user);

        return order;
    }

    public Page<Orders> getOrders(Long orderId, Pageable pageable) {
        return orderRepository.findAllByOrderId(orderId, pageable);
    }

    //주문 취소
    public void cancelOrder(Long orderId, Long userId) {
        User user = userService.validUserAllInfo(userId);
        Orders order = findVerifiedOrder(orderId);

        if (order.getOrdersStatus() == OrdersStatus.ORDER_COMPLETE) {
            order.setOrdersStatus(OrdersStatus.ORDER_CANCEL);

            //수량 변경 필수
            Cart cart = order.getCart();
            List<CartItem> optionList = cart.getCartItemList();

            for (CartItem cartItem: optionList) {
                int itemCount = cartItem.getItemCount();
                int itemCountRemain = cartItem.getOption().getItemCount();

                cartItem.getOption().setItemCount(itemCount + itemCountRemain);
            }
        } else if (order.getOrdersStatus() == OrdersStatus.ORDER_CANCEL) {
            throw new BusinessLogicException(OrderExceptionCode.ORDER_ALREADY_CANCEL);
        } else {
            throw new BusinessLogicException(OrderExceptionCode.ORDER_CANCEL_FAIL);
        }

    }

    public Orders findVerifiedOrder(long orderId) {
        Optional<Orders> findOrderId = orderRepository.findByOrderId(orderId);
        return findOrderId.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.ORDER_NOT_FOUND));
    }

    // 주문번호 조회
    private Orders findByOrderNumber(String orderNumber) {
        Optional<Orders> findByOrderNum = orderRepository.findByOrderNumber(orderNumber);
        return findByOrderNum.orElseThrow(() -> new BusinessLogicException(OrderExceptionCode.ORDER_NOT_FOUND));
    }

    // 주문번호 생성
    private String CreateOrderNumber() {
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
    // 배송전 변경 가능
    public void UpdateOrderStatus(String orderNumber, OrdersStatus ordersStatus) {
        Orders order = findByOrderNumber(orderNumber);
        if (checkStatus(order)) {
            order.setOrdersStatus(OrdersStatus.ORDER_CANCEL);
            System.out.println("주문이 취소됐습니다.");
        } else if (checkStatus(order)) {
            order.setOrdersStatus(OrdersStatus.PAYMENT_PROGRESS);
            System.out.println("결제 진행중입니다.");
        } else if (checkStatus(order)) {
            order.setOrdersStatus(OrdersStatus.PAYMENT_COMPLETE);
            System.out.println("결제가 완료됐습니다.");
        } else if (checkStatus(order)) {
            order.setOrdersStatus(OrdersStatus.PAYMENT_CANCEL);
            System.out.println("결제가 취소됐습니다.");
        } else if (checkStatus(order)) {
            order.setOrdersStatus(OrdersStatus.DELIVERY_PROGRESS);
            System.out.println("배송중 입니다.");
        } else if (checkStatus(order)) {
            order.setOrdersStatus(OrdersStatus.DELIVERY_COMPLETE);
            System.out.println("배송이 완료됐습니다.");
        } else if (checkStatus(order)) {
            order.setOrdersStatus(OrdersStatus.REFUND_PROGRESS);
            System.out.println("환불 진행중입니다.");
        } else {
            order.setOrdersStatus(OrdersStatus.REFUND_COMPLETE);
            System.out.println("환불 완료됐습니다.");
        }
    }

    // 주문 정보
    private boolean checkStatus(Orders order) {
        return order.getOrdersStatus().getIndex() >= OrdersStatus.ORDER_COMPLETE.getIndex();
    }
}

