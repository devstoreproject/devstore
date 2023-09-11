package project.main.webstore.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class OrderMonthlyPriceDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "Asia/Seoul")
    private LocalDate localDate;
    private Long totalDiscountedPrice;
    private Long totalOriginalPrice;

    public OrderMonthlyPriceDto(OrderDBMonthlyPriceDto dto) {
        this.localDate = LocalDate.of(dto.getYear(),dto.getMonth(),30);
        this.totalDiscountedPrice = dto.getTotalDiscountedPrice();
        this.totalOriginalPrice = dto.getTotalOriginalPrice();
    }
}
