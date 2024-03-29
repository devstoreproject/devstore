package project.main.webstore.domain.order.entity;

import static javax.persistence.FetchType.LAZY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.item.exception.ItemExceptionCode;
import project.main.webstore.domain.order.enums.OrdersStatus;
import project.main.webstore.domain.order.enums.PaymentType;
import project.main.webstore.domain.order.enums.TransCondition;
import project.main.webstore.domain.users.entity.ShippingInfo;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.valueObject.Address;

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
    private LocalDate deliveryDate;
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
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderedItem> orderedItemList;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Embedded
    private PaymentType paymentType;

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
    public Orders(String message, List<OrderedItem> orderedItemList,int deliveryPrice, User user, ShippingInfo info) {
        this.orderNumber = createOrderNumber();
        this.message = message;
        this.ordersStatus = OrdersStatus.ORDER_COMPLETE;
        this.recipient = info.getRecipient();
        this.address = info.getAddress();
        this.orderedItemList = orderedItemList;
        this.user = user;
        this.deliveryPrice = deliveryPrice;
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
        this.orderedItemList = orderedItemList != null ? orderedItemList : new ArrayList<>();
        this.user = user;
        this.paymentType = paymentType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalOrderedOriginalPrice() {
        if(orderedItemList!= null)
            return this.orderedItemList.stream().mapToInt(OrderedItem::getPrice).sum();
        return 0;
    }

    public int getTotalOrderedDiscountedPrice() {
        if(orderedItemList!= null)
            return this.orderedItemList.stream().mapToInt(OrderedItem::getDiscountedPrice).sum();
        return 0;
    }

    public void addDelivery(String trackingNumber, String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
        this.trackingNumber = trackingNumber;
    }

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
        return orderedItem.getOption().getItemCount() + orderedItem.getItemCount();
    }
}
