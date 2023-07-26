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
    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<ReviewImage> reviewImageList = new ArrayList<>();
    //삭제 시 주의할 점 ! 일단 무조건적인 삭제가 아니라 무조건 S3에서 삭제한 후 발동해야된다.

    //좋아요 count
    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likeList = new ArrayList<>();

    public void addReviewImage(ReviewImage reviewImage){
        this.reviewImageList.add(reviewImage);
        reviewImage.setReview(this);
    }

    // #### 편의 메서드 #### //
    public void addUserAndItem(User user, Item item){
        this.user = user;
        this.item = item;
    }

    public void addLike(Like like) {
        this.likeList.add(like);
        like.setReview(this);
    }
    //리뷰 생성할 때
    public Review(String comment, int rating, List<ReviewImage> reviewImageList, User user, Item item) {
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.item = item;
        this.reviewImageList = reviewImageList;
    }

    //리뷰 요청 받을 때
    public Review(ReviewPostRequestDto dto) {
        this.comment = dto.getComment();
        this.rating = dto.getRating();
    }

    @Builder(builderMethodName = "patchBuilder")
    public Review(Long id, String comment, Integer rating) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
    }

    @Builder(builderMethodName = "postBuilder")
    public Review(String comment, Integer rating) {
        this.comment = comment;
        this.rating = rating;
    }

    @Builder(builderMethodName = "stubBuilder",buildMethodName = "stubBuild")
    public Review(Long id, String comment, Integer rating, User user, Item item, List<ReviewImage> reviewImageList) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.item = item;
        this.reviewImageList = reviewImageList;
    }
}
