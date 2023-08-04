package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderLocalDto {
    private String message;
    private Long shippingId;
    private Long userId;
    private Long cartId;
    private Long orderId;


    public OrderLocalDto(OrderPostDto post) {
        this.message = post.getMessage();
        this.shippingId = post.getShippingInfoId();
        this.userId = post.getUserId();
        this.cartId = post.getCartId();
    }
    public OrderLocalDto(OrderPatchDto patch) {
        this.message = patch.getMessage();
        this.shippingId = patch.getShippingInfoId();
    }
}
