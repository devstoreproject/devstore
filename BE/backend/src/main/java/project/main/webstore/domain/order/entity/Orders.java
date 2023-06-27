package project.main.webstore.domain.order.entity;

import lombok.*;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.coupon.entity.Coupon;
import project.main.webstore.domain.order.dto.OrderFormPostDto;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.payment.entity.Payment;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PUBLIC;

@Getter
@Entity
@Table(name = "ORDERS")
@NoArgsConstructor
public class Orders extends Auditable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long orderId;
    @Setter
    private String orderNumber;
    @Setter
    private String recipient; // 배송받는사람
    @Setter
    private String email; // 이메일
    @Setter
    private String zipCode;
    @Setter
    private String addressSimple;
    @Setter
    private String addressDetail;
    @Setter
    private String mobileNumber; // 핸드폰번호
    @Setter
    private String homeNumber; // 유선번호
    @Setter
    private String message; // 배송요청사항

    // Dummy Data용
    @Setter
    private int itemCount;
    @Setter
    private String itemName;
    @Setter
    private int itemPrice;

    @Setter
    @Enumerated(value = EnumType.STRING)
    private OrdersStatus ordersStatus = OrdersStatus.ORDER_COMPLETE;

//    @Embedded
//    @Column(insertable = false, updatable = false)
//    private Address address;

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

    @ElementCollection(fetch = FetchType.LAZY)
    private List<Long> orderCartItem = new ArrayList<>();

    public void setOrderCartItem(Long cartItemId) {
        orderCartItem.add(cartItemId);
    }

    // 연관관계 매핑
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToMany(mappedBy = "order")
    private List<Coupon> coupons = new ArrayList<>();

    @OneToOne(mappedBy = "order")
    private Payment payment;

    public void setUser(User user) {
        this.user = user;
    }

        public void setOrderItems(OrderItem orderItem) {
        this.orderItems = orderItems;
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

    @Builder
    public Orders(String recipient, String email, String mobileNumber, String homeNumber,
                  String zipCode,String addressSimple,String addressDetail, String message) {
        this.recipient     = recipient;
        this.email         = email;
        this.mobileNumber  = mobileNumber;
        this.homeNumber    = homeNumber;
        this.message       = message;
        this.zipCode       = zipCode;
        this.addressSimple = addressSimple;
        this.addressDetail = addressDetail;
    }

    @Builder
    public Orders(Long orderId, String recipient, String email, String mobileNumber, String homeNumber,
                  String zipCode,String addressSimple,String addressDetail, String message) {
        this.orderId = orderId;
        this.recipient     = recipient;
        this.email         = email;
        this.mobileNumber  = mobileNumber;
        this.homeNumber    = homeNumber;
        this.message       = message;
        this.zipCode       = zipCode;
        this.addressSimple = addressSimple;
        this.addressDetail = addressDetail;

    }

    public Orders(int itemCount, String itemName, int itemPrice) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }



//    public static Orders toEntity(OrderFormPostDto orderFormPostDto) {
//        return Orders.builder()
//                .recipient(orderFormPostDto.getRecipient())
//                .email(orderFormPostDto.getEmail())
//                .mobileNumber(orderFormPostDto.getMobileNumber())
//                .homeNumber(orderFormPostDto.getHomeNumber())
//                .zipCode(orderFormPostDto.getZipCode())
//                .addressSimple(orderFormPostDto.getAddressSimple())
//                .addressDetail(orderFormPostDto.getAddressDetail())
//                .message(orderFormPostDto.getMessage())
//                .build();
//    }
}
