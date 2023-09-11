package project.main.webstore.domain.order.entity;

import lombok.*;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.item.exception.ItemExceptionCode;
import project.main.webstore.domain.order.enums.OrderedItem;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.enums.PaymentType;
import project.main.webstore.domain.order.enums.TransCondition;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.valueObject.Address;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

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
    @Column(updatable = false)
    private String orderNumber;
    @Setter
    @Column
    private String message;
    private int deliveryPrice;
    //송장 번호
    private String trackingNumber;
    private String deliveryCompany;

    @Setter
    @Builder.Default
    @Enumerated(value = EnumType.STRING)
    private OrdersStatus ordersStatus = OrdersStatus.ORDER_COMPLETE;

    //주소 정보 입력 (변동되어서는안되기 때문)
    @Setter
    @Column(updatable = false)
    private String recipient; // 배송받는사람

    @Setter
    @Embedded
    private Address address;

    //Cart에서 땡겨다가 쓸것들
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderedItem> orderedItemList;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Embedded
    private PaymentType paymentType;

    public void setUser(User user) {
        this.user = user;
    }


    public int getTotalOrderedOriginalPrice() {
        return this.orderedItemList.stream().mapToInt(OrderedItem::getPrice).sum();
    }

    public int getTotalOrderedDiscountedPrice() {
        return this.orderedItemList.stream().mapToInt(OrderedItem::getDiscountedPrice).sum();
    }

    public void addDelivery(String trackingNumber, String deliveryCompany){
        this.deliveryCompany = deliveryCompany;
        this.trackingNumber = trackingNumber;
    }

    //TODO: orderNumber -> entity method
    public String createOrderNumber() {
        Calendar cal = Calendar.getInstance();

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);

        StringBuilder builder = new StringBuilder();
        builder.append(y).append(m).append(d);

        for (int i = 0; i < 10; i++) {
            int random = (int) (Math.random() * 10);
            builder.append(random);
        }

        return builder.toString();
    }

    public void transItemCount(TransCondition transCondition) {
        if (transCondition.equals(TransCondition.MINUS)) {
            this.getOrderedItemList().forEach(orderedItem -> orderedItem.getOption().setItemCount(itemCountMinus(orderedItem)));
        } else {
            this.getOrderedItemList().forEach(orderedItem -> orderedItem.getOption().setItemCount(itemCountPlus(orderedItem)));
        }
    }

    private int itemCountMinus(OrderedItem orderedItem) {
        int result = orderedItem.getOption().getItemCount() - orderedItem.getItemCount();
        if (result < 0) {
            throw new BusinessLogicException(ItemExceptionCode.ITEM_NOT_ENOUGH);
        }
        return result;
    }

    private int itemCountPlus(OrderedItem orderedItem) {
        int result = orderedItem.getOption().getItemCount() + orderedItem.getItemCount();
        return result;
    }


    //TODO : 작업 중인 아이 지불 정보를 알고 있어야하는가 서버가?
    @Builder
    public Orders(String message, Cart cart, User user, ShippingInfo info) {
        this.orderNumber = createOrderNumber();
        this.message = message;
        this.ordersStatus = OrdersStatus.ORDER_COMPLETE;
        this.recipient = info.getRecipient();
        this.address = info.getAddress();
        this.orderedItemList = cart.getCartItemList().stream().map(OrderedItem::new).collect(Collectors.toList());
        this.user = user;
        this.deliveryPrice = cart.getDeliveryPrice();
    }

    public Orders(Long orderId, String message, int deliveryPrice, String trackingNumber, String deliveryCompany, OrdersStatus ordersStatus, String recipient, Address address, List<OrderedItem> orderedItemList, User user, PaymentType paymentType) {
        this.orderId = orderId;
        this.orderNumber = createOrderNumber();
        this.message = message;
        this.deliveryPrice = deliveryPrice;
        this.trackingNumber = trackingNumber;
        this.deliveryCompany = deliveryCompany;
        this.ordersStatus = ordersStatus;
        this.recipient = recipient;
        this.address = address;
        this.orderedItemList = orderedItemList;
        this.user = user;
        this.paymentType = paymentType;
    }
}
