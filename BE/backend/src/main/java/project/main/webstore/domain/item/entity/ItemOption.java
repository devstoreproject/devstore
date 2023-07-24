package project.main.webstore.domain.item.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "ITEM_OPTION")
@NoArgsConstructor(access = PROTECTED)
public class ItemOption {

    @Id
    @Setter
    @Column(name = "ITEM_OPTION_ID", updatable = false)
    @GeneratedValue(strategy = IDENTITY)
    private Long optionId;
    @Setter
    private String optionDetail;

    private int additionalPrice;
    @Setter
    private Integer itemCount;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Item_ID")
    private Item item;

    @Builder
    public ItemOption(Long optionId, String optionDetail, Item item,Integer itemCount) {
        this.optionId = optionId;
        this.itemCount = itemCount;
        this.optionDetail = optionDetail;
        this.item = item;
    }

    public ItemOption(String optionDetail, int itemCount,int additionalPrice, Item item) {
        this.optionDetail = optionDetail;
        this.itemCount = itemCount;
        this.additionalPrice = additionalPrice;
        this.item = item;
    }

    public ItemOption(String optionDetail, int itemCount,int additionalPrice) {
        this.optionDetail = optionDetail;
        this.itemCount = itemCount;
        this.additionalPrice = additionalPrice;
    }

    public ItemOption(int additionalPrice, Integer itemCount, Item item) {
        this.additionalPrice = additionalPrice;
        this.itemCount = itemCount;
        this.item = item;
    }
}
