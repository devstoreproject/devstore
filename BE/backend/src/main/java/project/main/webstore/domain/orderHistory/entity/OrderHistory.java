package project.main.webstore.domain.orderHistory.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.orderHistory.enums.OrderStatus;
import project.main.webstore.domain.payment.entity.Payment;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.valueObject.Address;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
    //TODO: ORDER 필드 객체 생성
    //TODO: ORDERNUMBER 생성 메서드 entity로 변경 필요...
    private String orderNumber;
    //TODO: ITEM 필드 객체 생성
    //TODO: ShippingInfo 객체
    @OneToOne
    // 주문 건당 -> 주문 내역에 들어감 -> 주문 내역에 들어간 아이템은 여러개가 들어온다.
    private Orders order;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private OrderStatus orderStatus = OrderStatus.INCART;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    private Item item;

    //    @Embedded
//    @AttributeOverrides(
//            @AttributeOverride(name = "value", column = @Column(name = "DELIVERY_PRICE"))
//    )
//    private Price deliveryPrice;
//    @Embedded
//    @AttributeOverrides(
//            @AttributeOverride(name = "value", column = @Column(name = "TOTAL_PRICE"))
//    )
//    private Price totalPrice;
//    @Embedded
//    @AttributeOverrides(
//            @AttributeOverride(name = "value", column = @Column(name = "DISCOUNT_PRICE"))
//    )
//    private Price discountedPrice;

}
