package project.main.webstore.domain.order.entity;

import lombok.*;
import project.main.webstore.domain.item.entity.Item;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Table(name = "ORDER_ITEM")
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID", updatable = false)
    private Long orderItemId;
    @Setter
    @Column(nullable = false)
    public int itemCount; // 주문할 아이템 수량


    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private Orders order;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private Item item;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ORDER_HISTORY_ID")
//    private OrderHistory orderHistory;

//    public void setOrder(Orders order) {
//        this.order = order;
//        if (!order.getOrderItems().contains(this)) {
//            order.getOrderItems().add(this);
//        }
//    }

}
