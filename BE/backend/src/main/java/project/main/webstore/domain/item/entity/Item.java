package project.main.webstore.domain.item.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.image.entity.ItemImage;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.enums.ItemStatus;
import project.main.webstore.domain.order.entity.OrderItem;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.valueObject.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;
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
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
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
    private Integer defaultCount;
    @Lob
    @Setter
    private String description;
//    @Column(nullable = false)
//    @Setter
//    private String detail;
//    @Column(nullable = false)
//    @Setter
//    private String specs;

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
    @OneToMany(mappedBy = "item",orphanRemoval = true,cascade = ALL)
    private List<ItemSpec> specList = new ArrayList<>();
    @OneToMany(mappedBy = "item",orphanRemoval = true,cascade = ALL)
    private List<ItemOption> optionList = new ArrayList<>();

    @OneToMany(mappedBy = "item",cascade = ALL)
    private List<CartItem> cartItemList = new ArrayList<>();
    @OneToMany(mappedBy = "item",cascade = ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();
    @OneToMany(mappedBy = "item",cascade = ALL,orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "item",cascade = ALL,orphanRemoval = true)
    private List<Question> questionList = new ArrayList<>();
    //PickedItem 연관관계 매핑
    @OneToMany(fetch = LAZY,cascade = ALL,mappedBy = "item")
    private List<PickedItem> pickedItem;

    public Item(Long itemId) {
        this.itemId = itemId;
    }

    @Builder(builderMethodName = "post")
    public Item(ItemPostDto post) {
        this.itemName = post.getName();
        this.defaultCount = post.getDefaultCount();
        this.description = post.getDescription();
        this.itemStatus = ItemStatus.ON_STACK;
        this.itemPrice = Price.builder().value(post.getItemPrice()).build();
        this.deliveryPrice = Price.builder().value(post.getDeliveryPrice()).build();
        this.category = post.getCategory();
        this.specList = post.getSpecList() != null ? post.getSpecList().stream().map(spec -> new ItemSpec(spec.getName(),spec.getContent(),this)).collect(Collectors.toList()) : null;
        this.optionList = post.getOptionList() != null ? post.getOptionList().stream().map(option -> new ItemOption(option.getOptionDetail(),option.getItemCount(),option.getAdditionalPrice(),this)).collect(Collectors.toList()) : null;
    }
    @Builder(builderMethodName = "patch")
    public Item(ItemPatchDto patch) {
        this.itemName = patch.getName();
        this.defaultCount = patch.getDefaultCount();
        this.description = patch.getDescription();
        this.itemPrice = Price.builder().value(patch.getItemPrice()).build();
        this.deliveryPrice = Price.builder().value(patch.getDeliveryPrice()).build();
        this.category = patch.getCategory();
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

    public int getTotalPrice(){
        if(optionList.isEmpty()){
            return 0;
        }
       return this.optionList.stream().mapToInt(ItemOption::getItemCount).sum();
    }

    // Price Method
    public void addPrice(Price itemPrice, Price deliveryPrice) {
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
    }
    
    public int getTotalCount(){
        if(this.optionList.isEmpty()){
            return defaultCount;
        }
        int totalOptionCount = optionList.stream().mapToInt(option -> option.getItemCount()).sum();
        return defaultCount + totalOptionCount;
    }
}

