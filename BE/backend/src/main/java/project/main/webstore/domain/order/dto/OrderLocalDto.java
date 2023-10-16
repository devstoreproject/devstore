package project.main.webstore.domain.order.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class OrderLocalDto {
    private String message;
    private Long shippingId;
    private Long userId;
    private Long cartId;
    private Long orderId;
    private List<Long> orderCartItemIdList;
}
