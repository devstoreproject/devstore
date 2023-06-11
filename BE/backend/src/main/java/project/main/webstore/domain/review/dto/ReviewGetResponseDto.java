package project.main.webstore.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

import static lombok.Builder.Default;

@Getter
public class ReviewGetResponseDto {
    private Long reviewId;
    private Long userId;
    private Long itemId;
    private String comment;
    List<String> imagePathList;

    @Builder(builderMethodName = "dtoBuilder", buildMethodName = "dtoBuild")
    public ReviewGetResponseDto(Review review) {
        this.reviewId = review.getId();
        this.userId = review.getUser().getId();
        this.itemId = review.getItem().getId();
        this.comment = review.getComment();
        this.imagePathList = List.of(review.getReviewImageList().get(0).getImagePath());
    }
}

