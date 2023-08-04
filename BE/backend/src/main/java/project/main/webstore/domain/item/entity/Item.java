package project.main.webstore.domain.item.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ItemImage;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.enums.ItemStatus;
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
public class Item extends Auditable {
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
    //    @Column(nullable = false)
//    @Setter
//    private Integer defaultCount;
    @Lob
    @Setter
    private String description;

    private long viewCount;

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
    @Setter
    private Integer discountRate;
    @Setter
    private Integer mileageRate;
    @Enumerated(STRING)
    @Setter
    private Category category;


    // 연관관계 매핑 //
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = ALL)
    private List<ItemSpec> specList = new ArrayList<>();
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = ALL)
    private List<ItemOption> optionList = new ArrayList<>();

    @OneToMany(cascade = ALL)
    private List<CartItem> cartItemList = new ArrayList<>();
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
    private List<Question> questionList = new ArrayList<>();
    //PickedItem 연관관계 매핑
    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "item")
    private List<PickedItem> pickedItem = new ArrayList<>();

    @OneToOne(cascade = ALL)
    @Setter
    private ItemOption defaultItem;

    public Item(Long itemId) {
        this.itemId = itemId;
    }

    public Item(ItemPostDto post) {
        this.itemName = post.getName();
        this.mileageRate = post.getMileageRate();
        this.description = post.getDescription();
        this.itemStatus = ItemStatus.ON_STACK;
        this.discountRate = post.getDiscountRate();
        this.itemPrice = Price.builder().value(post.getItemPrice()).build();
        this.deliveryPrice = Price.builder().value(post.getDeliveryPrice()).build();
        this.defaultItem = new ItemOption(0, post.getDefaultCount(), this);
        this.category = post.getCategory();
        this.specList = post.getSpecList() != null ? post.getSpecList().stream().map(spec -> new ItemSpec(spec.getName(), spec.getContent(), this)).collect(Collectors.toList()) : new ArrayList<>();
        this.optionList = post.getOptionList() != null ? post.getOptionList().stream().map(option -> new ItemOption(option.getOptionDetail(), option.getItemCount(), option.getAdditionalPrice(), this)).collect(Collectors.toList()) : new ArrayList<>();
    }

    public Item(ItemPatchDto patch) {
        this.itemName = patch.getName();
        this.mileageRate = patch.getMileageRate();
        this.description = patch.getDescription();
        this.discountRate = patch.getDiscountRate();
        this.defaultItem = new ItemOption(0, patch.getDefaultCount(), this);
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

    public void item(Long itemId) {
        this.itemId = itemId;
    }

    public void addItemImage(ItemImage image) {
        this.itemImageList.add(image);
        image.setItem(this);
    }

    public int getTotalPrice() {
        if (optionList.isEmpty()) {
            return 0;
        }
        return this.optionList.stream().mapToInt(ItemOption::getItemCount).sum();
    }

    public void addPrice(Price itemPrice, Price deliveryPrice) {
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
    }

    public int getTotalCount() {
        if (this.optionList.isEmpty()) {
            return 0;
        }
        return optionList.stream().mapToInt(ItemOption::getItemCount).sum();
    }

    public ItemImage getDefaultImage() {
        return itemImageList.stream().filter(Image::isRepresentative).findFirst().get();
    }

    public void addViewCount(){
        this.viewCount++;
    }

    @Builder(builderMethodName = "stub")
    public Item(List<ItemImage> itemImageList, Long itemId, String itemName, String description, Price itemPrice, Price deliveryPrice, Integer discountRate, Integer mileageRate, Category category, List<ItemSpec> specList, List<ItemOption> optionList, List<CartItem> cartItemList, List<OrderItem> orderItemList, List<Review> reviewList, List<Question> questionList, List<PickedItem> pickedItem, ItemOption defaultItem) {
        this.itemImageList = itemImageList;
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.viewCount = 0;
        this.itemStatus = ItemStatus.ON_STACK;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.discountRate = discountRate;
        this.mileageRate = mileageRate;
        this.category = category;
        this.specList = specList;
        this.optionList = optionList;
        this.cartItemList = cartItemList;
        this.orderItemList = orderItemList;
        this.reviewList = reviewList;
        this.questionList = questionList;
        this.pickedItem = pickedItem;
        this.defaultItem = defaultItem;
    }
}


