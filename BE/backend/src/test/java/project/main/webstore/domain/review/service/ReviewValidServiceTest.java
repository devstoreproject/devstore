package project.main.webstore.domain.review.service;


import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.exception.ReviewExceptionCode;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.exception.BusinessLogicException;

@ExtendWith(MockitoExtension.class)
class ReviewValidServiceTest {
    @InjectMocks
    ReviewValidService reviewValidService;
    @Mock
    ReviewRepository reviewRepository;
    ReviewStub reviewStub = new ReviewStub();

    @Test
    @DisplayName("리뷰 검증 성공")
    void review_valid_test() throws Exception{
        // given
        Review savedReview = reviewStub.createReview(1L, 2L, 3L);
        BDDMockito.given(reviewRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.of(savedReview));
        // when
        Review result = reviewValidService.validReview(1L);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(savedReview);
    }

    @Test
    @DisplayName("리뷰 검증 실패")
    void review_valid_fail_test() throws Exception{
        // given
        Review savedReview = reviewStub.createReview(1L, 2L, 3L);
        BDDMockito.given(reviewRepository.findById(ArgumentMatchers.anyLong())).willReturn(Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(()-> reviewValidService.validReview(1L)).hasMessage(ReviewExceptionCode.REVIEW_NOT_FOUND.getMessage());
    }

    protected Review validReview(Long reviewId) {
        Optional<Review> findReview = reviewRepository.findById(reviewId);
        if (findReview.isEmpty()) {
            throw new BusinessLogicException(ReviewExceptionCode.REVIEW_NOT_FOUND);
        }
        return findReview.get();
    }
}