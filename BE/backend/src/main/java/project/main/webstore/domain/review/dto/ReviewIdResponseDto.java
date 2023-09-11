package project.main.webstore.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "리뷰 Post Patch 응답 결과 (URL 이동을 위한 id값 포함)")
@AllArgsConstructor
public class ReviewIdResponseDto {
    @Schema(description = "리뷰 식별자")
    private Long reviewId;
    @Schema(description = "사용자 식별자")
    private Long userId;
    @Schema(description = "상품 식별자")
    private Long itemId;
}
