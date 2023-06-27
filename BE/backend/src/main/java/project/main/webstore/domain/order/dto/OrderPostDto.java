package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.order.entity.Orders;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderPostDto { // 주문한 아이템 정보 PostDto
    private List<OrderItemPostDto> orderItem;

}
