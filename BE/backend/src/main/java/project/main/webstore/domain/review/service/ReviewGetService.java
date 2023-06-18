package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.mapper.ReviewMapper;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.domain.users.entity.User;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewGetService {
    private final ReviewRepository reviewRepository;
    private final ReviewValidService reviewValidService;
    private final ReviewMapper mapper;

    public ReviewGetResponseDto getReviewByReviewId(Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);
        //추 후 삭제
        findReview.setUser(new User(1L));
        findReview.setItem(new Item(1L));
        return mapper.reviewGetResponse(findReview);
    }

    public ReviewGetResponseDto getReviewByUserId(Long userId, Long reviewId) {
        Review findReview = reviewValidService.validReviewByUserId(userId, reviewId);
        return mapper.reviewGetResponse(findReview);
    }

    public ReviewGetResponseDto getReviewByItemId(Long itemId, Long reviewId) {
        Review findReview = reviewValidService.validReviewByItemId(itemId, reviewId);
        return mapper.reviewGetResponse(findReview);
    }


    public Page<ReviewGetResponseDto> getReviewPage(Long userId,Pageable pageable){
        //TODO UserId를 이용해 검증할 필요가 있다. 관리자가 맞는지 검증할 필요가 있다.
        Page<Review> reviewPage = reviewRepository.findAllPage(pageable);
        return mapper.reviewGetPageResponse(reviewPage);
    }

    public Page<ReviewGetResponseDto> getReviewPageByUserId(Pageable pageable, Long userId) {
        //TODO User 검증 필요
        Page<Review> reviewPage = reviewRepository.findByUserIdPage(pageable, userId);
        return mapper.reviewGetPageResponse(reviewPage);
    }

    public Page<ReviewGetResponseDto> getReviewPageByItemId(Pageable pageable, Long itemId) {
        //TODO Item 검증 필요
        Page<Review> reviewPage = reviewRepository.findByItemIdPage(pageable, itemId);
        return mapper.reviewGetPageResponse(reviewPage);
    }
}
