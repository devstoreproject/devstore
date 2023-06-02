package project.main.webstore.domain.payment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.coupon.entity.Coupon;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;
import project.main.webstore.domain.payment.enums.PaymentStatus;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;

    @Enumerated(STRING)
    private PaymentStatus paymentStatus;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "COUPON_ID")
    private Coupon coupon;
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "ORDERHISTORY_ID")
    private OrderHistory orderHistory;
}
