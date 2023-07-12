package project.main.webstore.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.review.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReviewGetResponseDto {
    private Long reviewId;
    private Long userId;
    private Long itemId;
    private String comment;
    List<ImageDto> imageList;

    @Builder(builderMethodName = "dtoBuilder", buildMethodName = "dtoBuild")
    public ReviewGetResponseDto(Review review) {
        this.reviewId = review.getId();
        this.userId = review.getUser().getId();
        this.itemId = review.getItem().getItemId();
        this.comment = review.getComment();
        this.imageList = review.getReviewImageList() != null ? review.getReviewImageList().stream().map(ImageDto::new).collect(Collectors.toList()) : null;
    }
    //TODO: User, Item 엔티티 만들어지면 그 때 작업 진행하자.
}

