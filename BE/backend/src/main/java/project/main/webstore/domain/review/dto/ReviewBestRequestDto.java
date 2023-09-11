package project.main.webstore.domain.review.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ReviewBestRequestDto {
    List<Long> reviewIdList;

    public ReviewBestRequestDto(List<Long> reviewIdList) {
        this.reviewIdList = reviewIdList;
    }
}
