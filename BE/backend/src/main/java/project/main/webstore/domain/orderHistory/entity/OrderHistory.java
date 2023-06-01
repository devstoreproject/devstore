package project.main.webstore.domain.orderHistory.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.orderHistory.enums.OrderStatus;
import project.main.webstore.valueObject.Address;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private long id;
    private String orderNumber;
    private long deliveryPrice;
    private long totalPrice;
    private long discountedPrice;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private OrderStatus orderStatus = OrderStatus.INCART;
}
