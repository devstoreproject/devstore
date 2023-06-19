package project.main.webstore.domain.image.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.image.dto.ImageInfoDto;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Category")
@NoArgsConstructor
public class Image extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String originalName;
    private String uploadName;
    private String imagePath;
    private String ext;
    @Setter
    private int imageOrder;
    @Column(updatable = false)
    private String hash;
    private String thumbnailPath;
    @Setter
    private boolean isRepresentative;

    public Image(String originalName, String uploadName, String imagePath, String ext, String thumbnailPath, int imageOrder, boolean isRepresentative, String hash) {
        this.originalName = originalName;
        this.uploadName = uploadName;
        this.imagePath = imagePath;
        this.ext = ext;
        this.thumbnailPath = thumbnailPath;
        this.imageOrder = imageOrder;
        this.isRepresentative = isRepresentative;
        this.hash = hash;
    }
    public Image(Long id,String originalName, String uploadName, String imagePath, String ext, String thumbnailPath, int imageOrder, boolean isRepresentative, String hash) {
        this.id = id;
        this.originalName = originalName;
        this.uploadName = uploadName;
        this.imagePath = imagePath;
        this.ext = ext;
        this.thumbnailPath = thumbnailPath;
        this.imageOrder = imageOrder;
        this.isRepresentative = isRepresentative;
        this.hash = hash;
    }

    public Image(ImageInfoDto info, String imagePath, String thumbnailPath, String hash) {
        this.originalName = info.getOriginalName();
        this.uploadName = info.getUploadName();
        this.imagePath = imagePath;
        this.ext = info.getExt();
        this.thumbnailPath = thumbnailPath;
        this.imageOrder = info.getOrder();
        this.isRepresentative = info.isRepresentative();
        this.hash = hash;
    }

    //이미지 순서 및 대표값인지 여부 저장
    public void setOrderAndRepresentative(int order, boolean isRepresentative) {
        this.imageOrder = order;
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
