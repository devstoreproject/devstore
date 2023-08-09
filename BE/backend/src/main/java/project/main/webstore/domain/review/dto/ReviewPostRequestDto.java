package project.main.webstore.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "리뷰 Post 요청")
@Getter
@NoArgsConstructor
public class ReviewPostRequestDto {
    @Schema(description = "사용자 식별자",example = "1")
    private Long userId;
    @Schema(description = "리뷰 본문", example = "상품이 친절하고 사장님이 좋아요")
    private String comment;
    @Schema(description = "별점 최대 10점까지 가능",allowableValues = {"0","1","2","3","4","5","6","7","8","9","10"})
    private int rating;

    @Builder(builderMethodName = "stubBuilder", buildMethodName = "stubBuild")
    public ReviewPostRequestDto(Long userId, String comment, int rating) {
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
    }
}
