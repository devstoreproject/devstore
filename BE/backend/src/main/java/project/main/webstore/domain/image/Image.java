package project.main.webstore.domain.image;

import lombok.Getter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.enums.Category;
import project.main.webstore.domain.review.entity.Review;

import javax.persistence.*;

import static javax.persistence.FetchType.*;
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
    private String type;
    private int imageSort;

    //썸네일용 하나 추가

}
