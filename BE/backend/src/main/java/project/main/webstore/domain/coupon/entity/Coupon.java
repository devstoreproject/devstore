package project.main.webstore.domain.coupon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.coupon.enums.CouponStatus;
import project.main.webstore.domain.payment.entity.Payment;
import project.main.webstore.valueObject.Duration;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;
import static project.main.webstore.domain.coupon.enums.CouponStatus.BEFORE_USE;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Coupon extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String name;
    //사용 전 사용 후 만료
    private CouponStatus couponStatus = BEFORE_USE;
    private String serialNumber = UUID.randomUUID().toString();
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name="value",column = @Column(name = "DISCOUNT_PRICE"))
    )
    private Price discountPrice;
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name="value",column = @Column(name = "MINIMUM_ORDER_AMOUNT"))
    )
    private Price minimumPrice;
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name="value",column = @Column(name = "MAXIMUM_ADJUST_DISCOUNT"))
    )
    private Price maxAdjustedPrice;

    @Embedded
    private Duration duration;

    // 연관관계 매핑 //
    //user 여러개의 쿠폰이 여러개의 유저에 갈수 있자ㅛ   여러개의 쿠폰이 하나의 유저에 제공된다. 여러 유저는 하나의 쿠폰을 가진다.
    @OneToMany(mappedBy = "coupon")
    private List<CouponUser> couponUsers = new ArrayList<>();

    //payment  쿠폰 결제 : 하나의 결제에 여러개의 쿠폰을 사용할 수 있다. ->   여러개의 쿠폰이 하나의 결제에 사용이 가능하다. ManyToOne
    @ManyToOne(fetch = LAZY)
    private Payment payment;
}
