package project.main.webstore.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewPostRequestDto {
    private Long userId;
    private String comment;
    private int rating;
    private List<ImageSortDto> infoList;

}
