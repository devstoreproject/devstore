package project.main.webstore.domain.review.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.audit.Auditable;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.like.entity.Like;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.users.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
public class Review extends Auditable {
    @Id
    @GeneratedValue
    @Column(updatable = false)
    private Long id;
    @Setter
    private String comment;
    @Setter
    private Integer rating;
    @Setter
    private boolean best;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @Setter
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    @Setter
    private Item item;

    @Setter
    @OneToOne(mappedBy = "review", cascade = CascadeType.ALL)
    private ReviewImage reviewImage;
    //삭제 시 주의할 점 ! 일단 무조건적인 삭제가 아니라 무조건 S3에서 삭제한 후 발동해야된다.

    //좋아요 count
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>();

    //리뷰 생성할 때
    public Review(String comment, int rating, ReviewImage reviewImage, User user, Item item) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.item = item;
        this.reviewImage = reviewImage;
    }

    //리뷰 요청 받을 때
    public Review(ReviewPostRequestDto dto) {
        this.comment = dto.getComment();
        this.rating = dto.getRating();
    }

    @Builder(builderMethodName = "patchBuilder")
    public Review(Long id, String comment, Integer rating,Long userId, Long itemId) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.item = new Item(itemId);
        this.user = new User(userId);
    }

    @Builder(builderMethodName = "postBuilder")
    public Review(String comment, Integer rating,Long userId, Long itemId) {
        this.comment = comment;
        this.rating = rating;
        this.user = new User(userId);
        this.item = new Item(itemId);
    }

    @Builder(builderMethodName = "stubBuilder", buildMethodName = "stubBuild")
    public Review(Long id, String comment, Integer rating, User user, Item item, ReviewImage reviewImage) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.item = item;
        this.reviewImage = reviewImage;
    }

    public void addOrDeleteReviewImage(ReviewImage reviewImage) {
        if(reviewImage != null){
            this.reviewImage = reviewImage;
            reviewImage.setReview(this);
        }else
            this.reviewImage = null;
    }

    // #### 편의 메서드 #### //
    public void addUserAndItem(User user, Item item) {
        this.user = user;
        this.item = item;
    }

    public void addLike(Like like) {
        this.likeList.add(like);
        like.setReview(this);
    }
}
