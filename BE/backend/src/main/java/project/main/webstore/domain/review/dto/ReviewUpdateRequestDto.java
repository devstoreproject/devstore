package project.main.webstore.domain.review.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class ReviewUpdateRequestDto {
    private Long reviewId;
    @NotNull
    private Long userId;
    private String comment;
    private Integer rating;
    private List<Long> deleteImageId;
}
