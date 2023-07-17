package project.main.webstore.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import java.util.List;

@Schema(description = "리뷰 Post 요청")
@Getter
public class ReviewPostRequestDto {
    @Schema(description = "사용자 식별자",example = "1")
    private Long userId;
    @Schema(description = "리뷰 본문", example = "상품이 친절하고 사장님이 좋아요")
    private String comment;
    @Schema(description = "별점 최대 10점까지 가능",allowableValues = {"0","1","2","3","4","5","6","7","8","9","10"})
    private int rating;
    @Schema(description = "저장할 사진의 순서 및 대표 사진 정보",implementation = ImageSortDto.class)
    private List<ImageSortDto> infoList;

    @Builder(builderMethodName = "stubBuilder", buildMethodName = "stubBuild")
    public ReviewPostRequestDto(Long userId, String comment, int rating, List<ImageSortDto> infoList) {
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
        this.infoList = infoList;
    }
}
