package project.main.webstore.domain.users.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.users.dto.ShippingInfoPostDto;
import project.main.webstore.valueObject.Address;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@Table(name = "SHIPPING_INFO")
@NoArgsConstructor(access = PROTECTED)
public class ShippingInfo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name ="SHIPPING_INFO", updatable = false)
    private Long infoId;
    @Setter
    @Column(insertable = false, updatable = false)
    private String recipient; // 배송받는사람
    @Setter
    @Column(insertable = false, updatable = false)
    private String email; // 이메일
    @Setter
    @Column(insertable = false, updatable = false)
    private String zipCode;
    @Setter
    @Column(insertable = false, updatable = false)
    private String addressSimple;
    @Setter
    @Column(insertable = false, updatable = false)
    private String addressDetail;
    @Setter
    @Column(insertable = false, updatable = false)
    private String mobileNumber; // 핸드폰번호
    @Setter
    @Column(insertable = false, updatable = false)
    private String homeNumber; // 유선번호

    @Embedded
    private Address address;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders order;

    @Builder
    public ShippingInfo(Long infoId, String recipient, String email, String mobileNumber, String homeNumber,
                        String zipCode, String addressSimple, String addressDetail, Address address) {
        this.infoId = infoId;
        this.recipient = recipient;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.homeNumber = homeNumber;
        this.zipCode = zipCode;
        this.addressSimple = addressSimple;
        this.addressDetail = addressDetail;
        this.address = address;
    }



    public void setUser(User user) {
        this.user = user;
        if (user.getInfo() != this) {
            user.setInfo(this);
        }
    }

    public void setOrder(Orders order) {
        this.order = order;
    }
}

