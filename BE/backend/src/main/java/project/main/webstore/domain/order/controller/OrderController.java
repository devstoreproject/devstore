package project.main.webstore.domain.order.controller;


import com.querydsl.core.types.Order;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.order.dto.*;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.order.mapper.OrderMapper;
import project.main.webstore.domain.order.service.OrderService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Validated
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper  orderMapper;

    @PostMapping("/form")
    public ResponseEntity createOrderForm(@Valid @RequestBody OrderFormPostDto orderFormPostDto) {
        Orders order = orderMapper.orderFormPostDtoToOrderForm(orderFormPostDto);
//        Orders createOrderForm = orderService.writeOrderForm(order.getOrderId(), order);
        Orders               orderForm            = orderService.writeOrderForm(order);
        OrderFormResponseDto orderFormResponseDto = orderMapper.orderFormToOrderResponseDto(orderForm);

        return new ResponseEntity<>(orderFormResponseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/orders/editform")
    public ResponseEntity editOrderForm(@RequestBody @Valid OrderFormPatchDto orderFormPatchDto) {
        Orders order = orderMapper.orderFormPatchDtoToOrderForm(orderFormPatchDto);
        order.setOrderId(order.getOrderId());
        Orders patchOrderForm = orderService.editOrderForm(order);

        return new ResponseEntity<>(orderMapper.orderFormToOrderResponseDto(patchOrderForm), HttpStatus.OK);
    }

    @GetMapping("/{order-id}")
    public ResponseEntity<OrderDummyResponse> getOrder (@PathVariable("order-id") @Positive Long orderId) {

        Orders findOrder = orderService.getOrder(orderId);

        List<Orders> orderItemList = new ArrayList<>();
        orderItemList.add(new Orders(1, "MacBook", 1500000));
        orderItemList.add(new Orders(1, "MagicMouse", 100000));
        orderItemList.add(new Orders(1, "MagicKeyboard", 100000));

        OrderDummyResponse orderResponse = orderMapper.orderDummyToDummyResponse(findOrder, orderItemList);

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }


//    @PostMapping("/order/{order-id}")
//    public ResponseEntity createOrder(@PathVariable("order-id") Long orderId,
//                                      @Valid @RequestBody OrderPostDto orderPostDto) {
//        Orders order = orderMapper.orderPostDtoToOrder(orderPostDto);
//        Orders createOrder = orderService.createOrder(order);
//        OrderResponseDto orderResponseDto = orderMapper.orderToOrderResponseDto(createOrder);
//        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
//    }
}


