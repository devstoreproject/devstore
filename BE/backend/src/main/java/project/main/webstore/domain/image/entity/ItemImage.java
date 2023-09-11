package project.main.webstore.domain.image.entity;

import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.entity.Item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("ITEM")
@NoArgsConstructor
public class ItemImage extends Image {
    @Setter
    @ManyToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    public ItemImage(Image image, Item item) {
        super(image.getId() != null ? image.getId():null,image.getOriginalName(), image.getUploadName(), image.getImagePath(), image.getExt(), image.getThumbnailPath(), image.getImageOrder(), image.isRepresentative(),image.getHash());
        this.item = item;
    }
}
