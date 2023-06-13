package project.main.webstore.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReviewPostRequestDto {
    private Long userId;
    private String comment;
    private int rating;
    private List<Boolean> isRepresentativeList;
}
