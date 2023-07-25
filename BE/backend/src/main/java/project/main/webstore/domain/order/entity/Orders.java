package project.main.webstore.domain.order.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.coupon.entity.Coupon;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.dto.OrderPostDto;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.payment.entity.Payment;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.valueObject.Address;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
public class Orders extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long orderId;
    @Setter
    @Column(insertable = false, updatable = false)
    private String orderNumber;
    @Setter
    @Column(insertable = false, updatable = false)
    private String message; // 배송요청사항
    @Setter
    @Embedded
    private Address address;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private OrdersStatus ordersStatus = OrdersStatus.ORDER_COMPLETE;


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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    private ShippingInfo info;

    // 연관관계 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<Coupon> coupons = new ArrayList<>();
    @OneToOne(mappedBy = "order")
    private Payment payment;

    // item Method


    public void setUser(User user) {
        this.user = user;
    }

//    public void setItem(Item item) {
//        this.item = item;
//    }

//    public void setInfo(ShippingInfo info) {
//        this.
//    }

//    public void Item(Item item){
//        this.item = item;
//    }


    // orderItem Method
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void addOrderItems(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        if (orderItem.getOrder() != this) {
            orderItem.setOrder(this);
        }
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        if (payment.getOrder() != this) {
            payment.setOrder(this);
        }
    }

    public void setCoupons(Coupon coupon) {
        this.coupons = coupons;
        if (coupon.getOrder() != this) {
            coupon.setOrder(this);
        }
    }


    // Price Method
    public int getTotalPrice() {
        int totalPrice = 0;

        for (OrderItem orderItem: orderItems) {
            totalPrice += (orderItem.getItem().getItemPrice().getValue() * orderItem.getItemCount());
        }
        return totalPrice;
    }

    public Orders(String message, OrdersStatus ordersStatus) {
        this.message = message;
        this.ordersStatus = ordersStatus.ORDER_COMPLETE;
    }
}
