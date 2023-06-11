package project.main.webstore.domain.review.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;
    private String comment;
    private int rating;

    @ElementCollection
    List<String> imagePathList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Setter
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    @Setter
    private Item item;

    public Review(String comment, int rating, List<String> imagePathList, User user, Item item) {
        this.comment = comment;
        this.rating = rating;
        this.imagePathList = imagePathList;
        this.user = user;
        this.item = item;
    }

    public Review(ReviewPostRequestDto dto) {
        this.comment = dto.getComment();
        this.rating = dto.getRating();
        this.imagePathList = dto.getImagePathList();
    }
}
