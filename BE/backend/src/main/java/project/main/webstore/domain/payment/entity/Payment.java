package project.main.webstore.domain.payment.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Auditable;
import project.main.webstore.domain.coupon.entity.Coupon;
import project.main.webstore.domain.payment.enums.PaymentStatus;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private long Id;
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private Users user;
//    @ManyToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;
//    @ManyToOne
//    @JoinColumn(name = "orderHistory_id")
//    private OrderHistory orderHistory;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

}
