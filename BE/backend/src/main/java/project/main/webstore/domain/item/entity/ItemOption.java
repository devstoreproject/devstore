package project.main.webstore.domain.item.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.cart.entity.CartItem;
import project.main.webstore.domain.item.dto.OptionPatchDto;
import project.main.webstore.domain.order.enums.OrderedItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@Table(name = "ITEM_OPTION")
@NoArgsConstructor(access = PROTECTED)
public class ItemOption {
    @Id
    @Column(name = "ITEM_OPTION_ID", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long optionId;
    private String optionDetail;
    private String optionName;

    private Integer additionalPrice;
    @Setter
    private Integer itemCount;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Item_ID")
    private Item item;

    @OneToMany(mappedBy = "option",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<CartItem> cartItemList;
    @OneToMany(mappedBy = "option",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<OrderedItem> orderItem;
    @Builder
    public ItemOption(Long optionId, String optionDetail, Item item,Integer itemCount,String optionName) {
        this.optionId = optionId;
        this.itemCount = itemCount;
        this.optionDetail = optionDetail;
        this.item = item;
        this.optionName = optionName;
        this.cartItemList = new ArrayList<>();
        this.orderItem = new ArrayList<>();
    }
    public ItemOption(String optionDetail,Integer itemCount,int additionalPrice, Item item) {
        this.itemCount = itemCount;
        this.optionDetail = optionDetail;
        this.additionalPrice = additionalPrice;
        this.item = item;
        this.cartItemList = new ArrayList<>();
        this.orderItem = new ArrayList<>();
    }
    public ItemOption(String optionDetail, int itemCount,int additionalPrice,String optionName) {
        this.optionDetail = optionDetail;
        this.itemCount = itemCount;
        this.additionalPrice = additionalPrice;
        this.optionName = optionName;
        this.cartItemList = new ArrayList<>();
        this.orderItem = new ArrayList<>();
    }

    public ItemOption(int additionalPrice, Integer itemCount, Item item) {
        this.additionalPrice = additionalPrice;
        this.itemCount = itemCount;
        this.item = item;
        this.cartItemList = new ArrayList<>();
        this.orderItem = new ArrayList<>();
    }
    public ItemOption(OptionPatchDto dto) {
        this.optionId = dto.getOptionId();
        this.optionName = dto.getOptionName();
        this.additionalPrice = dto.getAdditionalPrice();
        this.optionDetail = dto.getOptionDetail();
        this.itemCount = dto.getItemCount();
        this.item = new Item();
        this.cartItemList = new ArrayList<>();
        this.orderItem = new ArrayList<>();
    }
}
