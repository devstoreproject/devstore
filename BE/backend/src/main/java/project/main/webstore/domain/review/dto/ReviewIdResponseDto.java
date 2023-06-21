package project.main.webstore.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewIdResponseDto {
    private Long reviewId;
    private Long userId;
    private Long itemId;
}
