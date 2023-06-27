package project.main.webstore.domain.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.querydsl.core.types.Order;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CollectionId;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.orderHistory.entity.OrderHistory;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "ORDER_ITEM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    private Long orderItemId;

    @Column(nullable = false)
    public int itemCount;

    @Column(nullable = false)   // Dummy Data
    public String itemName;

    @Column(nullable = false)   // Dummt Data
    public int itemPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDERS_ID")
    private Orders order;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_HISTORY_ID")
    private OrderHistory orderHistory;

    public void setOrder(Orders order) {
        this.order = order;
        if(!order.getOrderItems().contains(this)) {
            order.getOrderItems().add(this);
        }
    }

//    public void setItem(Item item) {
//        this.item = item;
//    }

    public OrderItem(int itemCount, String itemName, int itemPrice /*, Orders order, Item item*/) {
        this.itemCount = itemCount;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
//        this.order     = order;
//        this.item      = item;
    }

//
//   public void setOrderHistory(OrderHistory orderHistory) {
//        this.orderHistory = orderHistory;
//        if(!orderHistory.getOrderItems().contains(this)) {
//            orderHistory.getOrderItems().add(this);
//        }
//   }
}
