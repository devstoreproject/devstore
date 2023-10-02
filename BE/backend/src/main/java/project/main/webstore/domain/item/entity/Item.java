package project.main.webstore.domain.item.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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

@Getter
@Entity
@Builder
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "itemId")})
@NoArgsConstructor
@AllArgsConstructor
public class Item extends Auditable {
    @Setter
    @Default
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
    private List<ItemImage> itemImageList = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(nullable = false)
    @Setter
    private Long itemId;
    @Column(nullable = false)
    @Setter
    private String itemName;
    @Lob
    @Setter
    private String description;

    private long viewCount;

    @Setter
    @Default
    @Enumerated(STRING)
    private ItemStatus itemStatus = ItemStatus.ON_STACK;

    @Setter
    @Column(nullable = false)
    private Integer itemPrice;

    @Setter
    @Column(nullable = false)
    private Integer deliveryPrice;
    @Setter
    private Integer discountRate;
    private int salesQuantity;
    @Enumerated(STRING)
    @Setter
    private Category category;
    @Transient
    @Setter
    private boolean like;

    // 연관관계 매핑 //
    @Default
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = ALL)
    private List<ItemOption> optionList = new ArrayList<>();
    @Default
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
    private List<Review> reviewList = new ArrayList<>();
    @Default
    @OneToMany(mappedBy = "item", cascade = ALL, orphanRemoval = true)
    private List<Question> questionList = new ArrayList<>();
    //PickedItem 연관관계 매핑
    @Default
    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "item")
    private List<PickedItem> pickedItem = new ArrayList<>();

    @OneToOne(cascade = ALL)
    @Setter
    private ItemOption defaultItem;
    @Setter
    @Default
    @OneToMany(cascade = ALL,orphanRemoval = true)
    private List<CartItem> cartItemList = new ArrayList<>();
    public Item(Long itemId) {
        this.itemId = itemId;
        this.defaultItem = new ItemOption();
        this.optionList = new ArrayList<>();
        this.cartItemList = new ArrayList<>();
        this.reviewList = new ArrayList<>();
        this.questionList = new ArrayList<>();
        this.pickedItem = new ArrayList<>();
    }

    public Item(ItemPostDto post) {
        this.itemName = post.getName();
        this.description = post.getDescription();
        this.itemStatus = ItemStatus.ON_STACK;
        this.discountRate = post.getDiscountRate();
        this.itemPrice = post.getItemPrice();
        this.deliveryPrice = post.getDeliveryPrice();
        this.defaultItem = new ItemOption(0, post.getDefaultCount(),this);
        this.category = post.getCategory();
        this.optionList = post.getOptionList() != null ? post.getOptionList().stream().map(option -> new ItemOption(option.getOptionDetail(), option.getAdditionalPrice(), option.getItemCount(),option.getOptionName(), this)).collect(Collectors.toList()) : new ArrayList<>();
    }

    public Item(ItemPatchDto patch, Long itemId) {
        this.itemId = itemId;
        this.itemName = patch.getName();
        this.description = patch.getDescription();
        this.discountRate = patch.getDiscountRate();
        this.defaultItem = new ItemOption(0, patch.getDefaultCount(), this);
        this.itemPrice = patch.getItemPrice();;
        this.deliveryPrice = patch.getDeliveryPrice();
        this.category = patch.getCategory();
        this.optionList = patch.getUpdateOptionList() != null ? patch.getUpdateOptionList().stream().map(ItemOption::new).collect(Collectors.toList()) : new ArrayList<>();
    }



    public Item(List<ItemImage> itemImageList, Long itemId, String itemName, String description, Integer itemPrice, Integer deliveryPrice, Integer discountRate, Category category, List<ItemOption> optionList, List<CartItem> cartItemList, List<Review> reviewList, List<Question> questionList, List<PickedItem> pickedItem, ItemOption defaultItem) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.viewCount = 0;
        this.itemStatus = ItemStatus.ON_STACK;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.discountRate = discountRate;
        this.category = category;
        this.optionList = optionList;
        this.cartItemList = cartItemList;
        this.reviewList = reviewList;
        this.questionList = questionList;
        this.pickedItem = pickedItem;
        this.defaultItem = defaultItem;
        this.itemImageList = itemImageList;
    }

    public Item(Long itemId, String itemName, String description,Integer itemPrice, Integer deliveryPrice, Integer discountRate, Category category, List<ItemOption> optionList) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.viewCount = 0L;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.discountRate = discountRate;
        this.category = category;
        this.optionList = optionList;
        this.cartItemList = new ArrayList<>();
        this.reviewList = new ArrayList<>();
        this.questionList = new ArrayList<>();
        this.pickedItem = new ArrayList<>();
        this.defaultItem = new ItemOption(0,1000,this);
        this.itemStatus = ItemStatus.ON_STACK;
    }

    public Item(Long itemId, Integer itemPrice, Integer deliveryPrice, Integer discountRate) {
        this.itemId = itemId;
        this.itemPrice = itemPrice;
        this.deliveryPrice = deliveryPrice;
        this.discountRate = discountRate;
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

    public int getTotalCount() {
        if (this.optionList.isEmpty()) {
            return 0;
        }
        return optionList.stream().mapToInt(ItemOption::getItemCount).sum();
    }

    public ItemImage getDefaultImage() {
        return itemImageList.stream().filter(Image::isRepresentative).findFirst().orElse(null);
    }

    public void addViewCount() {
        this.viewCount++;
    }
    public void addSalesQuantity(int count){
        this.salesQuantity += count;
    }
    public void minusSalesQuantity(int count){
        this.salesQuantity -= count;
    }
    public List<ItemOption> getOptionListWithOutDefault(){
        if(optionList == null)
            return new ArrayList<>();
        for (ItemOption itemOption : optionList) {
            if(itemOption.isDefaultOption()){
                optionList.remove(itemOption);
                break;
            }
        }
        return optionList;
    }
    public void addOptionList(List<ItemOption> itemOptionList) {
        for (ItemOption itemOption : itemOptionList) {
            this.optionList.add(itemOption);
            itemOption.setItem(this);
        }
    }
    public void addDefaultItem(ItemOption itemOption){
        this.defaultItem = itemOption;
        itemOption.setItem(this);
    }
}


