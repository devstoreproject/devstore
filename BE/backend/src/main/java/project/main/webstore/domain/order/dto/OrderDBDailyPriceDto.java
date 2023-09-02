package project.main.webstore.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderDBDailyPriceDto {
    private Integer year;
    private Integer month;
    private Integer day;
    private Long totalDiscountedPrice;
    private Long totalOriginalPrice;
}
