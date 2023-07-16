package project.main.webstore.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.review.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Schema(description = "리뷰 단건 조회")
@Getter
public class ReviewGetResponseDto {
    @Schema(description = "이미지 경로, 썸네일 경로, 대표 이미지, 이미지 순서가 포함된 정보", implementation = ImageDto.class)
    List<ImageDto> imageList;
    @Schema(description = "리뷰 식별자")
    private Long reviewId;
    @Schema(description = "사용자 식별자")
    private Long userId;
    @Schema(description = "상품 식별자")
    private Long itemId;
    @Schema(description = "리뷰 본문")
    private String comment;

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

