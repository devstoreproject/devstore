package project.main.webstore.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewUpdateRequestDto {
    @NotNull
    private Long userId;
    private String comment;
    private Integer rating;
    //여기서 이미지를 제거한다.
    private List<Long> deleteImageId;
    //전체 사진을 다시 받아온다? 이거 리소스에 상당한 낭비를 가져올 것 같다.
    //요청 시 마다 모든 파일을 전달한다면?
    private List<ImageSortDto> imageSortAndRepresentativeInfo;
}
