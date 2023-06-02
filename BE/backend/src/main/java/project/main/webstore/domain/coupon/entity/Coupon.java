package project.main.webstore.domain.coupon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.coupon.enums.CouponStatus;
import project.main.webstore.valueObject.Duration;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

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
    private CouponStatus couponStatus;
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

}
