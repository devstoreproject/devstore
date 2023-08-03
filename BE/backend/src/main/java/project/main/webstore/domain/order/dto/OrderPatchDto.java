package project.main.webstore.domain.order.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPatchDto {
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "특수문자 제외 20자 이내로 작성하세요")
    @Size(min = 0, max = 20)
    private String message;

    private Long shippingInfoId;

//    private List<OrderItemPostDto> patchItemCount; 수량 변동은 Cart 에서만 진행할 예정입니다.
}
