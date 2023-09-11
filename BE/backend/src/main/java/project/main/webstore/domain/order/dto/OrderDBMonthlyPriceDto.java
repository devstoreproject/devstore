package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDBMonthlyPriceDto {
    private Integer year;
    private Integer month;
    private Long totalDiscountedPrice;
    private Long totalOriginalPrice;
}
