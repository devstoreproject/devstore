package project.main.webstore.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.dto.*;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.exception.BusinessLogicException;
import project.main.webstore.domain.order.exception.ExceptionCode;
import project.main.webstore.domain.order.repository.OrderItemRepository;
import project.main.webstore.domain.order.repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Slf4j
//@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository     orderRepository;
    private final OrderItemRepository orderItemRepository;
//    private final OrderMapper orderMapper;
//    private final UserRepository userRepository;
//    private final ItemRepository itemRepository;
//    private final ItemService itemService;
//    private final UserRepository userRepository;


    // 현재 user, item domain 연결(X)
    // 주문서 폼 작성, 해당 정보 저장

    // Mapper를 이용한 DTO변환
//    public OrderFormResponseDto writeOrderFrom (OrderFormPostDto orderFormPostDto) {
//        Orders orderForm = orderMapper.orderFormPostDtoToOrderForm(orderFormPostDto);
//        orderForm.setOrdersStatus(OrdersStatus.ORDER_COMPLETE);
//        Orders orderForm = new Orders();
//        Orders saveOrderForm = orderRepository.save(orderForm);
//        OrderFormResponseDto orderFormResponseDto = orderMapper.orderFormToOrderResponseDto(saveOrderForm);
//        return orderFormResponseDto;
//    }
    public Orders writeOrderForm(Orders order) {    // BeanUtils 사용하여 코드 간략화해보기
//        Orders orderForm = findOrder(orderId);
        Orders orderForm = new Orders();
        orderForm.setRecipient(order.getRecipient());
        orderForm.setEmail(order.getEmail());
        orderForm.setMobileNumber(order.getMobileNumber());
        orderForm.setHomeNumber(order.getHomeNumber());
        orderForm.setZipCode(order.getZipCode());
        orderForm.setAddressSimple(order.getAddressSimple());
        orderForm.setAddressDetail(order.getAddressDetail());
        orderForm.setMessage(order.getMessage());

        return orderRepository.save(orderForm);
    }

//    patchform 보류  작성된 주문서 수정
    public Orders editOrderForm(Orders order) {
        Orders patchOrderForm = findOrder(order.getOrderId());

        return orderRepository.save(patchOrderForm);
    }

    public Orders findOrder(long orderId) {
        Optional<Orders> findByOrderId = orderRepository.findById(orderId);
        Orders foundOrder = findByOrderId.orElseThrow(() -> new BusinessLogicException(ExceptionCode.ORDER_NOT_FOUND));

        return foundOrder;
    }

    public Orders getOrder(long orderId) {
    return findOrder(orderId);
    }

    // 주문 정보 생성 service
    // orderItemPostDto -> cartItemId, itemId, itemCount에 필요한 Item, CartItem repository 필요
//    public Orders createOrder(Orders order,  OrderPostDto orderPostDto, Long orderId) {
//        Orders order = orderRepository.findById(orderId).orElse(null);
//        if (order == null) {
//            return null;
//        }
//        order.setOrdersStatus(OrdersStatus.ORDER_COMPLETE);
//
//        for(OrderItemPostDto orderItemPostDto : orderPostDto.getOrderItem()) {
//            Long cartItemId = orderItemPostDto.getCartItemId(); // orderItemPostDto <- set/get 넣기
//            Long itemId = orderItemPostDto.getItemId();
//            long itemCount = orderItemPostDto.getItemCount();
//            order.setOrderCartItem(cartItemId);
//            order.setOrderCartItem(itemId);
//            order.setOrderCartItem(itemCount);
//
//            Item item = itemService.findByItemId(orderItemPostDto.getItemId());
//            OrderItem orderItem = new OrderItem(
//        }
//        return orderRepository.save(order);
//    }

}

