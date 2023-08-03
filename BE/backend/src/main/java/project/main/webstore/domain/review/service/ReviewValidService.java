package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.exception.ReviewExceptionCode;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.exception.BusinessLogicException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewValidService {
    private final ReviewRepository reviewRepository;

    protected Review validReview(Long reviewId) {
        Optional<Review> findReview = reviewRepository.findById(reviewId);
        if (findReview.isEmpty()) {
            throw new BusinessLogicException(ReviewExceptionCode.REVIEW_NOT_FOUND);
        }
        return findReview.get();
    }

    protected Review validReviewByUserId(Long userId,Long reviewId) {
        Optional<Review> findReview = reviewRepository.findByUserId(userId,reviewId);
        if (findReview.isEmpty()) {
            throw new BusinessLogicException(ReviewExceptionCode.REVIEW_NOT_FOUND);
        }
        return findReview.get();
    }

    protected Review validReviewByItemId(Long itemId, Long reviewId) {
        Optional<Review> findReview = reviewRepository.findByItemId(itemId,reviewId);
        if (findReview.isEmpty()) {
            throw new BusinessLogicException(ReviewExceptionCode.REVIEW_NOT_FOUND);
        }
        return findReview.get();
    }
    protected List<Review> validReviewList(List<Long> reviewIdList) {
        List<Review> list = reviewRepository.findByIdList(reviewIdList);
        if(list == null)
            return new ArrayList<>();
        return list;
    }
}
