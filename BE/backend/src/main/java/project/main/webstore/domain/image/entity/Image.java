package project.main.webstore.domain.image.entity;

import lombok.Getter;
import project.main.webstore.audit.Auditable;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;
import static javax.persistence.InheritanceType.*;

@Entity
@Getter
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "Category")
public class Image extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;
    private String realTitle;
    private String savedTitle;
    private String imagePath;
    private String ext;
    private int imageOrder;

    //썸네일용 하나 추가
    private String thumbnailPath;
}
