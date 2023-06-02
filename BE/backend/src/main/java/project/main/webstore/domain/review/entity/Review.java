package project.main.webstore.domain.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private long id;
    private String comment;
    private int rating;

    @ElementCollection
    List<String> imagePathList = new ArrayList<>();

}
