package project.main.webstore.domain.review.service;


import java.util.List;
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

@ExtendWith(MockitoExtension.class)
class ReviewValidServiceTest {

    @InjectMocks
    ReviewValidService reviewValidService;
    @Mock
    ReviewRepository reviewRepository;
    ReviewStub reviewStub = new ReviewStub();

    @Test
    @DisplayName("리뷰 검증 성공")
    void review_valid_test() throws Exception {
        // given
        Review savedReview = reviewStub.createReview(1L, 2L, 3L);
        BDDMockito.given(reviewRepository.findById(ArgumentMatchers.anyLong()))
                .willReturn(Optional.of(savedReview));
        // when
        Review result = reviewValidService.validReview(1L);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(savedReview);
    }

    @Test
    @DisplayName("리뷰 검증 실패")
    void review_valid_fail_test() throws Exception {
        // given
        Review savedReview = reviewStub.createReview(1L, 2L, 3L);
        BDDMockito.given(reviewRepository.findById(ArgumentMatchers.anyLong()))
                .willReturn(Optional.empty());
        // when
        // then
        Assertions.assertThatThrownBy(() -> reviewValidService.validReview(1L))
                .hasMessage(ReviewExceptionCode.REVIEW_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("리뷰 리스트 검증 성공")
    void review_list_valid_test() throws Exception {
        // given
        List<Review> savedReview = reviewStub.createList();
        BDDMockito.given(reviewRepository.findByIdList(ArgumentMatchers.anyList()))
                .willReturn(savedReview);
        // when
        List<Review> result = reviewValidService.validReviewList(List.of(1L, 2L));
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(savedReview);
    }

    @Test
    @DisplayName("리뷰 리스트 검증 실패")
    void review_list_valid_fail_test() throws Exception {
        BDDMockito.given(reviewRepository.findByIdList(ArgumentMatchers.anyList()))
                .willReturn(null);
        // when
        List<Review> result = reviewValidService.validReviewList(List.of(1L, 2L));
        // then
        Assertions.assertThat(result).isEmpty();
    }

}