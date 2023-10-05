package project.main.webstore.domain.order.dto;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderTrackingInfoDto {
    @NotBlank
    private String trackingNumber;
    private String deliveryCompany;
}
