package project.main.webstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.order.dto.OrderDBDailyPriceDto;
import project.main.webstore.domain.order.dto.OrderDBItemSaleDto;
import project.main.webstore.domain.order.dto.OrderDBMonthlyPriceDto;
import project.main.webstore.domain.order.dto.OrderLocalDto;
import project.main.webstore.domain.order.entity.OrderedItem;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.enums.TransCondition;
import project.main.webstore.domain.order.exception.OrderExceptionCode;
import project.main.webstore.domain.order.repository.OrderRepository;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserValidService userService;

    public Orders createOrder(OrderLocalDto post) {
        User user = userService.validUserAllInfo(post.getUserId());
        ShippingInfo shippingInfo = user.getShippingInfo(post.getShippingId());

        Cart cart = user.getCart();
        //장바구니 비우기
        user.setCart(null);
        Orders order = new Orders(post.getMessage(), cart, user, shippingInfo);
        order.transItemCount(TransCondition.MINUS);
        List<OrderedItem> orderedItemList = order.getOrderedItemList();
        //여기
        for (OrderedItem orderedItem : orderedItemList) {
            Item item = orderedItem.getItem();
            item.addSalesQuantity(orderedItem.getItemCount());
        }
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

    public Page<Orders> getOrders(Pageable pageable, Long userId,Integer month) {
        if (userId.equals(-1L)) {
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_LOGIN);
        } else if (userId.equals(0L)) {
            //관리자 일 경우
            if(month != null){
                LocalDateTime now = LocalDateTime.now().minusMonths(month);

                return orderRepository.findByMonth(pageable,now);
            }else {
                return orderRepository.findAll(pageable);
            }
        } else {
            //회원 일 경우
            if(month != null){
                LocalDateTime now = LocalDateTime.now().minusMonths(month);
                return orderRepository.findByUserIdAndMonth(pageable,userId,now);
            }else {
                return orderRepository.findAllByUserId(pageable, userId);
            }
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

    public List<OrderDBMonthlyPriceDto> getMonthlyPrice(){
        return orderRepository.monthlyPrice();
    }
    public List<OrderDBDailyPriceDto> getDailyPrice(){
        return orderRepository.dailyPrice();
    }

    public List<OrderDBItemSaleDto> getItemPrice(){
        return orderRepository.itemSales();
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

    public Orders setTrackingNumber(Long orderId,String trackingNumber,String company) {
        Orders findOrder = validOrder(orderId);
        findOrder.addDelivery(trackingNumber,company);
        findOrder.setOrdersStatus(OrdersStatus.DELIVERY_PROGRESS);
        return findOrder;
    }

    //배송 완료일로부터 7일 이전에만 가능하다.
    public Orders refundOrder(Long userId, Long orderId, List<Long> optionId) {
        //사용자
        Orders findOrder = validOrder(orderId);

        //사용자 검증
        userService.validUserIdSame(userId, findOrder);

        //주문 7일 이전 것들만 취소 가능
        LocalDate deliveryDate = findOrder.getDeliveryDate();
        LocalDate now = LocalDate.now();
        if(deliveryDate == null || check7Days(deliveryDate, now)){
            List<OrderedItem> itemList = findOrder.getOrderedItemList();
            for (Long id : optionId) {
                OrderedItem orderedItem = itemList.stream().filter(orderItem -> orderItem.getOrder().getOrderId().equals(id)).findFirst().orElseThrow(()->new BusinessLogicException(OrderExceptionCode.ORDER_ITEM_NOT_FOUND));
                ItemOption option = orderedItem.getOption();
                Integer itemCount = option.getItemCount();
                option.setItemCount(itemCount + orderedItem.getItemCount());
            }
            findOrder.setOrdersStatus(OrdersStatus.ORDER_CANCEL);
        }
        return findOrder;
    }

    private boolean check7Days(LocalDate deliveryDate, LocalDate now) {
        return now.getMonthValue() - deliveryDate.getMonthValue() == 0 && now.getYear() - deliveryDate.getYear() == 0 && now.getDayOfMonth() - deliveryDate.getDayOfMonth() < 7;
    }

    public Page<Orders> getOrderByStatus(Pageable pageable,String status) {
        OrdersStatus value = OrdersStatus.of(status);
        return orderRepository.findByOrdersStatus(pageable, value);
    }

    public Orders changStatus(Long orderId,String status) {
        OrdersStatus value = OrdersStatus.of(status);

        if(value == OrdersStatus.DELIVERY_COMPLETE)
            return orderStatusComplete(orderId);
        else if (value == OrdersStatus.ORDER_CANCEL) {
            return orderStatusCancel(orderId);
        }else {
            Orders findOrder = validOrder(orderId);
            findOrder.setOrdersStatus(value);
            return findOrder;
        }
    }
    public Orders orderStatusComplete(Long orderId) {
        Orders findOrder = validOrder(orderId);
        findOrder.setOrdersStatus(OrdersStatus.DELIVERY_COMPLETE);
//        List<OrderedItem> orderedItemList = findOrder.getOrderedItemList();
//        for (OrderedItem orderedItem : orderedItemList) {
//            Item item = orderedItem.getItem();
//            item.addSalesQuantity(orderedItem.getItemCount());
//        }
        return findOrder;
    }
    public Orders orderStatusCancel(Long orderId) {
        Orders findOrder = validOrder(orderId);
        findOrder.setOrdersStatus(OrdersStatus.ORDER_CANCEL);
        List<OrderedItem> orderedItemList = findOrder.getOrderedItemList();
        for (OrderedItem orderedItem : orderedItemList) {
            Item item = orderedItem.getItem();
            item.minusSalesQuantity(orderedItem.getItemCount());
        }
        return findOrder;
    }
}

