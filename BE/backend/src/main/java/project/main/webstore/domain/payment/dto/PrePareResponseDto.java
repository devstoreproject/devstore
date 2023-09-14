package project.main.webstore.domain.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PrePareResponseDto {
    private String orderNumber;
    private Long amount;

}
