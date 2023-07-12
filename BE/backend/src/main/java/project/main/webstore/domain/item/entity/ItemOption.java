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

    //TODO : 옵션별 수량 존재하지 않는
    private int itemCount;

    @Setter
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "Item_ID")
    private Item item;

    @Builder
    public ItemOption(Long optionId, String optionDetail, Item item) {
        this.optionId = optionId;
        this.optionDetail = optionDetail;
        this.item = item;
    }
}
