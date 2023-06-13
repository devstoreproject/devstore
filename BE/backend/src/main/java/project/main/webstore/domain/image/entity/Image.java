package project.main.webstore.domain.image.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.JOINED;

@Entity
@Getter
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "Category")
@NoArgsConstructor
public class Image extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String realTitle;
    private String savedTitle;
    private String imagePath;
    private String ext;
    @Setter
    private int imageOrder;

    //썸네일용 하나 추가
    private String thumbnailPath;
    @Setter
    private boolean isRepresentative;

    //이미지 순서 및 대표값인지 여부 저장
    public void setOrderAndRepresentative(int order, boolean isRepresentative){
        this.imageOrder = order;
        this.isRepresentative = isRepresentative;
    }

    public Image(String realTitle, String savedTitle, String imagePath, String ext, String thumbnailPath, int imageOrder, boolean isRepresentative) {
        this.realTitle = realTitle;
        this.savedTitle = savedTitle;
        this.imagePath = imagePath;
        this.ext = ext;
        this.thumbnailPath = thumbnailPath;
        this.imageOrder = imageOrder;
        this.isRepresentative = isRepresentative;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id.equals(image.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
