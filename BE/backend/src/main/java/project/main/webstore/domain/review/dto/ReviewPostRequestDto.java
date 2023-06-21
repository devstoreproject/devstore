package project.main.webstore.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import java.util.List;

@Getter
public class ReviewPostRequestDto {
    private Long userId;
    private String comment;
    private int rating;
    private List<ImageSortDto> infoList;

    @Builder(builderMethodName = "stubBuilder", buildMethodName = "stubBuild")
    public ReviewPostRequestDto(Long userId, String comment, int rating, List<ImageSortDto> infoList) {
        this.userId = userId;
        this.comment = comment;
        this.rating = rating;
        this.infoList = infoList;
    }
}
