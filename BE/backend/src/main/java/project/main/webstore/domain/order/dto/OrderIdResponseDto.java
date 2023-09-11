package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderIdResponseDto {
    private Long orderId;

    public OrderIdResponseDto(Long orderId) {
        this.orderId = orderId;
    }
}
