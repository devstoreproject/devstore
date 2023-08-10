package project.main.webstore.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Schema(description="리뷰 Patch 응답 스키마" )
@NoArgsConstructor
public class ReviewUpdateRequestDto {
    @NotNull
    @Schema(description = "사용자 식별자")
    private Long userId;
    @Schema(description = "수정할 리뷰 본문")
    private String comment;
    @Schema(description = "수정할 별점",allowableValues = {"0","1","2","3","4","5","6","7","8","9","10"})
    private Integer rating;

    public ReviewUpdateRequestDto(Long userId, String comment, Integer rating) {
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
    }
}
