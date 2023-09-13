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

    public void addUserId(Long userId){
        this.userId = userId;
    }
    public OrderLocalDto(OrderPostDto post) {
        this.message = post.getMessage();
        this.shippingId = post.getShippingInfoId();
        this.orderCartItemIdList = post.getCartItemIdList();
    }
    public OrderLocalDto(OrderPatchDto patch) {
        this.message = patch.getMessage();
        this.shippingId = patch.getShippingInfoId();
    }
}
