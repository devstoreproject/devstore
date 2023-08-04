package project.main.webstore.domain.users.entity;

import lombok.*;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.valueObject.Address;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Builder
@Entity
@Getter
@Table(name = "SHIPPING_INFO")
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class ShippingInfo {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "SHIPPING_INFO", updatable = false)
    private Long infoId;
    @Setter
    @Column(updatable = false)
    private String recipient; // 배송받는사람

    @Setter
    @Embedded
    private Address address;

    @Setter
    @ManyToOne(fetch = LAZY) // 배송지 여러개 등록 가능
    @JoinColumn(name = "USER_ID")
    private User user;

    @Setter
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders order;

    public ShippingInfo(Long infoId, String recipient, Address address, User user) {
        this.infoId = infoId;
        this.recipient = recipient;
        this.address = address;
        this.user = user;
    }
}

