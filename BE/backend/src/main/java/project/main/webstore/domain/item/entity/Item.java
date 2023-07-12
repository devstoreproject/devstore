package project.main.webstore.domain.item.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.image.entity.ItemImage;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.enums.ItemStatus;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "itemId")})
@NoArgsConstructor(access = PROTECTED)
public class Item {
    @Setter
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ItemImage> itemImageList = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false)
    @Setter
    private Long itemId;
    @Column(nullable = false)
    @Setter
    private String itemName;
    @Column(nullable = false)
    @Setter
    private int itemCount;
    @Lob
    @Setter
    private String description;
    @Column(nullable = false)
    @Setter
    private String detail;
    @Column(nullable = false)
    @Setter
    private String specs;

    //상세 정보

    @Enumerated(STRING)
    private ItemStatus itemStatus = ItemStatus.ON_STACK;

    @Embedded
    @Setter
    @Column(nullable = false)

    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "ITEM_PRICE"))
    )
    private Price itemPrice;

    @Embedded
    @Setter
    @Column(nullable = false)
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "DELIVERY_PRICE"))
    )
    private Price deliveryPrice;

    @Enumerated(STRING)
    @Setter
    private Category category;

    // 연관관계 매핑 //
    @OneToMany(mappedBy = "item")
    private List<ItemSpec> specList = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<ItemOption> optionList = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItemList = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "item")
    private List<Question> questionList = new ArrayList<>();
    //PickedItem 연관관계 매핑
    @OneToOne(fetch = LAZY)
    private PickedItem pickedItem;

    public Item(PickedItem pickedItem) {
        this.pickedItem = pickedItem;
    }

    public Item(Long itemId) {
        this.itemId = itemId;
    }


    @Builder
    public Item(Long itemId, String itemName, int itemCount, String description,
                Price itemPrice, Price deliveryPrice, Category category) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.description = description;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.category = category;
    }

    // Price Method
    public Item(Price itemPrice, Price deliveryPrice) {
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
    }

    public void addSpec(ItemSpec itemSpec) {
        this.specList.add(itemSpec);
        itemSpec.setItem(this);
        if (itemSpec.getItem() != this) {
            itemSpec.setItem(this);
        }
    }

    public void addOption(ItemOption itemOption) {
        this.optionList.add(itemOption);
        itemOption.setItem(this);
        if (itemOption.getItem() != this) {
            itemOption.setItem(this);
        }
    }


//    public void addCartItemLIst(CartItem cartItem) {
//        this.cartItemList.add(cartItem);
//        cartItem.setItem(this);
//        if(cartItem.getItem() != this) {
//            cartItem.setItem(this);
//        }
//    }

    public void addReview(Review review) {
        this.reviewList.add(review);
        review.setItem(this);
        if (review.getItem() != this) {
            review.setItem(this);
        }
    }

    public void addQuestion(Question question) {
        questionList.add(question);
        question.setItem(this);
        if (question.getItem() != this) {
            question.setItem(this);
        }
    }

    // Item builder
    public void item(Long itemId) {
        this.itemId = itemId;
    }

    public void addItemImage(ItemImage image) {
        this.itemImageList.add(image);
        image.setItem(this);
    }
}

