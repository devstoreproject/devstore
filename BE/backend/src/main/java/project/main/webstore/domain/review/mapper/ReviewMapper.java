package project.main.webstore.domain.review.mapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.review.dto.*;
import project.main.webstore.domain.review.entity.Review;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewMapper {
    public Review toEntity(ReviewPostRequestDto post){
        return Review.postBuilder()
                .rating(post.getRating())
                .comment(post.getComment())
                .build();
    }
    public Review toEntity(ReviewPostRequestDto post,Long userId,Long itemId){
        return Review.postBuilder()
                .rating(post.getRating())
                .comment(post.getComment())
                .userId(userId)
                .itemId(itemId)
                .build();
    }

    public Review toEntity(ReviewUpdateRequestDto patch,Long reviewId,Long userId,Long itemId){
        return Review.patchBuilder()
                .id(reviewId)
                .comment(patch.getComment())
                .rating(patch.getRating())
                .itemId(itemId)
                .userId(userId)
                .build();
    }

    public ReviewIdResponseDto toDto(Review review){
        return new ReviewIdResponseDto(review.getId(),review.getUser().getId(),review.getItem().getItemId());
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

    public ReviewLikeResponseDto toDto(Boolean like){
        return new ReviewLikeResponseDto(like);
    }
}
