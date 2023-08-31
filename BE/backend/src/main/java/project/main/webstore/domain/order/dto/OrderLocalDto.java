package project.main.webstore.domain.order.dto;

import lombok.*;

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

    public void addUserId(Long userId){
        this.userId = userId;
    }
    public OrderLocalDto(OrderPostDto post) {
        this.message = post.getMessage();
        this.shippingId = post.getShippingInfoId();
    }
    public OrderLocalDto(OrderPatchDto patch) {
        this.message = patch.getMessage();
        this.shippingId = patch.getShippingInfoId();
    }
}
