package project.main.webstore.domain.orderHistory.entity;

import lombok.*;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.Cart;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.order.entity.Orders;
import project.main.webstore.domain.orderHistory.enums.OrderStatus;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.valueObject.Address;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderHistory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    // 주문 건당 -> 주문 내역에 들어감 -> 주문 내역에 들어간 아이템은 여러개가 들어온다.
    // TODO: order에서 아이템 관련 정보 가져오기
    @OneToOne
    private Orders order;

    @Embedded
    private Address address;

    @Enumerated(STRING)
    private OrderStatus orderStatus = OrderStatus.INCART;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
    public void setOrder(Orders order) {
        this.order = order;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
