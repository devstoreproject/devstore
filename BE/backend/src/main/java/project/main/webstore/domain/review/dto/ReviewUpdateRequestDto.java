package project.main.webstore.domain.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Schema(description="리뷰 Patch 응답 스키마" )
@AllArgsConstructor
public class ReviewUpdateRequestDto {
    @NotNull
    @Schema(description = "사용자 식별자")
    private Long userId;
    @Schema(description = "수정할 리뷰 본문")
    private String comment;
    @Schema(description = "수정할 별점",allowableValues = {"0","1","2","3","4","5","6","7","8","9","10"})
    private Integer rating;
    //여기서 이미지를 제거한다.
    @Schema(description = "삭제할 이미지 식별자 리스트")
    private List<Long> deleteImageId;
    //전체 사진을 다시 받아온다? 이거 리소스에 상당한 낭비를 가져올 것 같다.
    //요청 시 마다 모든 파일을 전달한다면?

    @Schema(description = "저장할 사진의 순서 및 대표 사진 정보",implementation = ImageSortDto.class)
    private List<ImageSortDto> imageSortAndRepresentativeInfo;
}
