package project.main.webstore.domain.review.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.dto.ReviewIdResponseDto;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {

    private Long userId;
    private String comment;
    private int rating;
    private List<ImageSortDto> infoList;

    public Review toEntity(ReviewPostRequestDto post){
        return Review.postBuilder()
                .rating(post.getRating())
                .comment(post.getComment())
                .build();
    }

    public Review toEntity(ReviewUpdateRequestDto patch,Long reviewId){
        return Review.patchBuilder()
                .id(reviewId)
                .comment(patch.getComment())
                .rating(patch.getRating())
                .build();
    }

    public ReviewIdResponseDto toDto(Review review){
//        return new ReviewIdResponseDto(review.getId(),review.getUser().getId(),review.getItem().getId());
        return new ReviewIdResponseDto(review.getId(),1L,1L);
    }
    public ReviewGetResponseDto toGetDtoResponse(Review review){
        return ReviewGetResponseDto.dtoBuilder()
                .review(review)
                .dtoBuild();
    }

    public Page<ReviewGetResponseDto> toGetPageResponse(Page<Review> reviewPage){
        return reviewPage.map(ReviewGetResponseDto::new);
    }
    public List<ReviewGetResponseDto> toGetListResponse(List<Review> reviewList){
        return reviewList.stream().map(ReviewGetResponseDto::new).collect(Collectors.toList());
    }

    public Slice<ReviewGetResponseDto> toGetSliceResponse(Slice<Review> reviewSlice){
        return reviewSlice.map(ReviewGetResponseDto::new);
    }
}
