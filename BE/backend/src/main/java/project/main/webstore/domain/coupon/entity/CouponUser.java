package project.main.webstore.domain.coupon.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class CouponUser {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    
    // 연관관계 매핑 //
    @ManyToOne
    private Coupon coupon;
    @ManyToOne
    private User user;

}
