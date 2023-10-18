package project.main.webstore.domain.review.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.dto.Dto;

@Schema(description = "리뷰 단건 조회")
@Getter
public class ReviewGetResponseDto implements Dto {

    @Schema(description = "리뷰 식별자")
    private Long reviewId;
    @Schema(description = "사용자 식별자")
    private Long userId;
    @Schema(description = "작성자", example = "김복자")
    private String userName;
    @Schema(description = "상품 식별자")
    private Long itemId;
    @Schema(description = "리뷰 본문")
    private String comment;
    @Schema(description = "리뷰 베스트 여부")
    private boolean best;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    @Schema(description = "이미지 경로, 썸네일 경로, 대표 이미지, 이미지 순서가 포함된 정보")
    ImageDto image;
    @Builder(builderMethodName = "dtoBuilder", buildMethodName = "dtoBuild")
    public ReviewGetResponseDto(Review review) {
        this.reviewId = review.getId();
        this.userId = review.getUser().getId();
        this.itemId = review.getItem().getItemId();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
        this.modifiedAt = review.getModifiedAt();
        this.userName = review.getUser().getNickName();
        this.best = review.isBest();
        this.image = review.getReviewImage() != null ? new ImageDto(review.getReviewImage()):null;
    }
}
//닉네임

