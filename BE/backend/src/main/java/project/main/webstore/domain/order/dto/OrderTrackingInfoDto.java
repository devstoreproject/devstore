package project.main.webstore.domain.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class OrderTrackingInfoDto {
    @NotBlank
    private String trackingNumber;
    private String deliveryCompany;
}
