package project.main.webstore.domain.review.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {
    public ReviewGetResponseDto reviewGetResponse(Review review){
        return ReviewGetResponseDto.dtoBuilder()
                .review(review)
                .dtoBuild();
    }

    public List<ReviewGetResponseDto> reviewGetListResponse(List<Review> reviewList) {
        return reviewList.stream()
                .map(this::reviewGetResponse)
                .collect(Collectors.toList());
    }

    public Page<ReviewGetResponseDto> reviewGetPageResponse(Page<Review> reviewPage){
        return reviewPage.map(ReviewGetResponseDto::new);
    }

    public Slice<ReviewGetResponseDto> reviewGetSliceResponse(Slice<Review> reviewSlice){
        return reviewSlice.map(ReviewGetResponseDto::new);
    }
}
