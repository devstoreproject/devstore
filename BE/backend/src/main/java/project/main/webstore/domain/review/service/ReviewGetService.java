package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewGetService {
    private final ReviewRepository reviewRepository;
    private final ReviewValidService reviewValidService;

    public Review getReviewByReviewId(Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);
        return findReview;
    }


    public Page<Review> getReviewPage(Pageable pageable) {
        Page<Review> reviewPage = reviewRepository.findAllPage(pageable);
        return reviewPage;
    }

    public Page<Review> getReviewPageByUserId(Pageable pageable, Long userId) {
        Page<Review> reviewPage = reviewRepository.findByUserIdPage(pageable, userId);
        return reviewPage;
    }

    public Page<Review> getReviewPageByItemId(Pageable pageable, Long itemId) {
        Page<Review> reviewPage = reviewRepository.findByItemIdPage(pageable, itemId);
        return reviewPage;
    }

    public List<Review> getBestReview(Long itemId, int count) {
        List<Review> allList = reviewRepository.findByItemId(itemId);
        Collections.sort(allList, Comparator.comparingInt(s -> s.getLikeList().size()));
        List<Review> answer = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            answer.add(allList.get(i));
        }
        return answer;
    }
}
