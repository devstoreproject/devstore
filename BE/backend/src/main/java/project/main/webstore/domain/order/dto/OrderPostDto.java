package project.main.webstore.domain.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPostDto {
    @Setter
    @Schema(hidden = true)
    private Long userId;
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$", message = "특수문자 제외 20자 이내로 작성하세요")
    @Size(min = 0, max = 20)
    private String message;
    private Long shippingInfoId;
    private List<Long> cartItemIdList;
}