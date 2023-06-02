package project.main.webstore.domain.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    RETURN("반품 신청"),
    PICKUP("재품 수거"),
    CHECK("검수중"),
    APPROVE("반품 승인"),
    DENY("반품 거절"),
    COMPLETE("반품 완료");

    PaymentStatus(String progress){
    }
}