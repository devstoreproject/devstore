package project.main.webstore.domain.order.entity;

import lombok.*;
import org.springframework.security.core.parameters.P;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "ORDER_ITEM")
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID", updatable = false)
    private Long orderItemId;
    @Setter
    @Column(name ="ITEM_ID", insertable = false, updatable = false)
    private Long itemId;
    @Setter
    @Column(nullable = false)
    public int itemCount; // 주문할 아이템 수량
    @Setter
    @Column(nullable = false)
    public String itemName;
    @Setter
    @Column(nullable = false)
    public int itemPrice;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_HISTORY_ID")
    private OrderHistory orderHistory;

//    public void setOrder(Orders order) {
//        this.order = order;
//        if (!order.getOrderItems().contains(this)) {
//            order.getOrderItems().add(this);
//        }
//    }

    public void setItem(Item item) {
        this.item = item;
        if (!item.getOrderItemList().contains(this)) {
            item.getOrderItemList().add(this);
        }
    }

    @Builder
    public OrderItem(Item item, Long itemId, int itemCount, String itemName, int itemPrice) {
        this.item = item;
        this.itemId = itemId;
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }

        public Item getItem() {
        return this.item;
    }
//    public void OrderItem(List<OrderItem> orderItems) {
//        this.OrderItem(orderItems);
//    }


    //   public void setOrderHistory(OrderHistory orderHistory) {
//        this.orderHistory = orderHistory;
//        if(!orderHistory.getOrderItems().contains(this)) {
//            orderHistory.getOrderItems().add(this);
//        }
//   }
}
