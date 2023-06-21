package project.main.webstore.domain.like.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Table(name = "Likes")
@NoArgsConstructor(access = PROTECTED)
public class Like extends Auditable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(updatable = false)
    private Long id;

    //연관관계 매핑
    // item
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "ITEM_ID")
//    private Item item;
    //user
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = LAZY)
    @Setter
    private Review review;

    public Like(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public Like(Review review) {
        this.review = review;
    }
}
