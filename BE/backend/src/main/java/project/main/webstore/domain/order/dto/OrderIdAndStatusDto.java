package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.order.enums.OrdersStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderIdAndStatusDto {
    private Long orderId;
    private OrdersStatus ordersStatus;
}
