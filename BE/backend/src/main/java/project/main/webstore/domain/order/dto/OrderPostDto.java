package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPostDto { // 주문정보 (유저, 배송지, 카트 정보)
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "특수문자 제외 20자 이내로 작성하세요")
    @Size(min = 0, max = 20)
    private String message;
    private Long shippingInfoId;
    private Long userId;
    private Long cartId;
}
