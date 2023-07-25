package project.main.webstore.domain.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@NoArgsConstructor
public class OrderPostDto { // 주문한 아이템 정보 PostDto
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "특수문자 제외 20자 이내로 작성하세요")
    @Size(min = 0, max = 20)
    private String message;
    @NotNull
    private List<OrderItemPostDto> orderItems;
    //TODO;
    private Long shippingInfoId;
    private Long userId;

    @Builder
    public OrderPostDto(String message, List<OrderItemPostDto> orderItems, Long shippingInfoId, Long userId) {
        this.message = message;
        this.orderItems = orderItems;
        this.shippingInfoId = shippingInfoId;
        this.userId = userId;
    }
}
