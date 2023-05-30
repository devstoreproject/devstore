package project.main.webstore.domain.review.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.audit.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue
    @Column(unique = true, updatable = false)
    private long id;
    private String imagePath;
    private String comment;
    private int rating;

}
