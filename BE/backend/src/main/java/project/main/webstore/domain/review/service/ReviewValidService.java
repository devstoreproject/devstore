package project.main.webstore.domain.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.exception.ReviewExceptionCode;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.exception.BusinessLogicException;

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

    protected List<Review> validReviewList(List<Long> reviewIdList) {
        List<Review> list = reviewRepository.findByIdList(reviewIdList);
        if(list == null)
            return new ArrayList<>();
        return list;
    }
}
