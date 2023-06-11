package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.mapper.ReviewMapper;
import project.main.webstore.domain.review.repository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewGetService {
    private final ReviewRepository reviewRepository;
    private final ReviewValidService validService;
    private final ReviewMapper mapper;

    public ReviewGetResponseDto getReviewByReviewId(Long reviewId) {
        Review findReview = validService.validReview(reviewId);
        return mapper.reviewGetResponse(findReview);
    }

    public ReviewGetResponseDto getReviewByUserId(Long userId, Long reviewId) {
        Review findReview = validService.validReviewByUserId(userId, reviewId);
        return mapper.reviewGetResponse(findReview);
    }

    public ReviewGetResponseDto getReviewByItemId(Long itemId, Long reviewId) {
        Review findReview = validService.validReviewByItemId(itemId, reviewId);
        return mapper.reviewGetResponse(findReview);
    }

    public List<ReviewGetResponseDto> getReviewList(Long userId){
        //TODO UserId를 이용해 검증할 필요가 있다. 관리자가 맞는지 검증할 필요가 있다.
        List<Review> reviewList = reviewRepository.findAll();
        return mapper.reviewGetListResponse(reviewList);
    }
    public List<ReviewGetResponseDto> getReviewListByUserId(Long userId) {
        //TODO User가 존재하는지 검증하는 로직 필요 이 로직은 User 담당자가 구현 한 이후 추가 예정
        List<Review> reviewList = reviewRepository.findByUserId(userId);
        return mapper.reviewGetListResponse(reviewList);
    }
    public List<ReviewGetResponseDto> getReviewListByItemId(Long itemId) {
        //TODO Item이 존재하는지 검증하는 로직 필요 이 로직은 Item 담당자가 구현한 이후 추가 예정
        List<Review> reviewList = reviewRepository.findByItemId(itemId);
        return mapper.reviewGetListResponse(reviewList);
    }

    public Page<ReviewGetResponseDto> getReviewPageByUserId(Pageable pageable, Long userId) {
        //TODO User 검증 필요
        Page<Review> reviewPage = reviewRepository.findByUserIdPage(pageable, userId);
        return mapper.reviewGetPageResponse(reviewPage);
    }

    public Slice<ReviewGetResponseDto> getReviewSliceByUserId(Pageable pageable, Long userId) {
        //TODO User 검증 필요
        Slice<Review> reviewSlice = reviewRepository.findByUserIdSlice(pageable, userId);
        return mapper.reviewGetSliceResponse(reviewSlice);
    }

    public Page<ReviewGetResponseDto> getReviewPageByItemId(Pageable pageable, Long itemId) {
        //TODO User 검증 필요
        Page<Review> reviewPage = reviewRepository.findByUserIdPage(pageable, itemId);
        return mapper.reviewGetPageResponse(reviewPage);
    }

    public Slice<ReviewGetResponseDto> getReviewSliceByItemId(Pageable pageable, Long itemId) {
        //TODO User 검증 필요
        Slice<Review> reviewSlice = reviewRepository.findByUserIdSlice(pageable, itemId);
        return mapper.reviewGetSliceResponse(reviewSlice);
    }
}
