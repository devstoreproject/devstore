package project.main.webstore.domain.orderHistory.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.orderHistory.enums.OrderStatus;
import project.main.webstore.domain.payment.entity.Payment;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.valueObject.Address;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String orderNumber;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "DELIVERY_PRICE"))
    )
    private Price deliveryPrice;
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "TOTAL_PRICE"))
    )
    private Price totalPrice;
    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "DISCOUNT_PRICE"))
    )
    private Price discountedPrice;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private OrderStatus orderStatus = OrderStatus.INCART;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    @OneToOne(fetch = LAZY)
    private Cart cart;

    //TODO: Payment 연관관계 추가
    @OneToOne(mappedBy = "orderHistory")
    private Payment payment;

    public void setPayment(Payment payment) {
        this.payment = payment;
        if(payment.getOrderHistory() != this) {
//            payment.setOrderHistory(this);
        }
    }

    //TODO: OrderItem 추가
    @OneToMany(mappedBy = "orderHistory")
    private List<OrderItem> orderItems;
}
