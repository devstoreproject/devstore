package project.main.webstore.domain.item.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.enums.ItemStatus;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
@NoArgsConstructor(access = PROTECTED)
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String name;
    private int itemCount;

    @ElementCollection(fetch = EAGER)
    List<String> imagePath = new ArrayList<>();

    @Lob
    private String description;
    //상세 정보

    @Enumerated
    private ItemStatus itemStatus = ItemStatus.ON_STACK;
    @Embedded
    private Price price;
    @Enumerated
    private Category category;

    @OneToMany(mappedBy = "item")
    private List<Spec> specList = new ArrayList<>();
    @OneToMany(mappedBy = "cart")
    private List<CartItem> cartItems = new ArrayList<>();

    //PicedItem 연관관계 매핑
    @OneToOne(fetch = LAZY)
    private PickedItem pickedItem;

}
