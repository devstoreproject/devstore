package project.main.webstore.domain.item.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "ITEM_SPEC")
@NoArgsConstructor(access = PROTECTED)
public class ItemSpec {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name ="ITEM_SPEC_ID", nullable = false)
    @Setter
    private Long specId;
    @Setter
    private String name;
    @Setter
    private String content;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public void writeSpec(Long specId, String content) {
        this.specId = specId;
        this.content = content;
    }

    @Builder
    public ItemSpec(Long specId, String name, String content) {
        this.specId   = specId;
        this.name = name;
        this.content  = content;
    }

    public ItemSpec(String name, String content, Item item) {
        this.name = name;
        this.content = content;
        this.item = item;
    }

    // specPatchDto Builder
    public void specPatchDto(ItemSpec specPatch) {
        this.name = specPatch.getItem().getItemName();
        this.content = specPatch.getContent();
    }
    /*
   - spec 작성시 해당 item과 itemId를 가져와 연동하여 작성해야 되는지?
   - post시 item/{item-id}/spec (?)
     */

}
