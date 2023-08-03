package project.main.webstore.domain.order.entity;

import lombok.*;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
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

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Builder
@Table(name = "ORDERS")
@NoArgsConstructor
@AllArgsConstructor
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
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private OrdersStatus ordersStatus = OrdersStatus.ORDER_COMPLETE;

    @OneToOne
    private Cart cart;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    private ShippingInfo info;

    // 연관관계 매핑
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<Coupon> coupons = new ArrayList<>();
    @OneToOne(mappedBy = "order")
    private Payment payment;

    public void setUser(User user) {
        this.user = user;
    }

    public Long getCartId() {
        return cart.getId();
    }

    public Long getInfoId() {
        return info.getInfoId();
    }

    // orderItem Method
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
        return this.cart.getOriginalTotalPrice();
    }

    public int getDiscountPrice(){
        return this.cart.getDiscountedTotalPrice();
    }
    public int getDeliveryPrice(){
        return this.cart.getDeliveryPrice();
    }
    public Orders(String message, OrdersStatus ordersStatus) {
        this.message = message;
        this.ordersStatus = ordersStatus.ORDER_COMPLETE;
    }

    public Orders(String orderNumber, String message, Cart cart, User user, ShippingInfo info, List<Coupon> coupons, Payment payment) {
        this.orderNumber = orderNumber;
        this.message = message;
        this.ordersStatus = OrdersStatus.ORDER_COMPLETE;
        this.cart = cart;
        this.user = user;
        this.info = info;
        this.coupons = coupons;
        this.payment = payment;
    }
}
