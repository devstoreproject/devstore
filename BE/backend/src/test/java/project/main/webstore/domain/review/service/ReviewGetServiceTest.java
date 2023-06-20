package project.main.webstore.domain.review.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.exception.BusinessLogicException;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewGetServiceTest {
    @InjectMocks
    private ReviewGetService reviewGetService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewValidService reviewValidService;
    private ReviewStub reviewStub = new ReviewStub();

    private Long reviewId = 1L;
    private Long itemId = 1L;
    private Long userId = 1L;

    @Test
    @DisplayName("ReviewGetResponseDto  : 성공")
    void getReviewByReviewIdTest() throws Exception {
        // given
        Review review = reviewStub.createReview(userId, itemId, reviewId);
        ReviewGetResponseDto expected = reviewStub.reviewGetResponseDto(review);

        given(reviewValidService.validReview(ArgumentMatchers.anyLong())).willReturn(review);
        // when
        Review result = reviewGetService.getReviewByReviewId(reviewId);
        // then
        Assertions.assertThat(result.getItem().getId()).isEqualTo(review.getItem().getId());
        Assertions.assertThat(result.getUser().getId()).isEqualTo(review.getUser().getId());
        Assertions.assertThat(result.getId()).as("assertion 수행 이전에 넣어줘야한다.즉 맨 마지막에 나오면 에러 터짐 ㅎㅎ").isEqualTo(reviewId);
    }

    @Test
    @DisplayName("ReviewGetResponseDto  : 실패")
    void getReviewByReviewIdExceptionTest() throws Exception {
        // given
        BDDMockito.given(reviewValidService.validReview(ArgumentMatchers.anyLong())).willThrow(BusinessLogicException.class);
        // when then
        Assertions.assertThatThrownBy(() -> reviewGetService.getReviewByReviewId(reviewId)).isInstanceOf(BusinessLogicException.class);
    }

    @Test
    @DisplayName("관리자의 리뷰 전체 조회(페이지) : 성공")
    void getReviewPageTest() throws Exception {
        int page = 0;
        int size = 3;
        Page<Review> reviewPage = reviewStub.createPageReview(page, size);
        PageRequest pageInfo = PageRequest.of(0, 3);
        // given
        BDDMockito.given(reviewRepository.findAllPage(ArgumentMatchers.any(Pageable.class))).willReturn(reviewPage);

        // when
        Page<Review> result = reviewGetService.getReviewPage(userId, pageInfo);
        // then
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(reviewPage);
        Assertions.assertThat(result.getSize()).isEqualTo(size);
    }

    @Test
    @DisplayName("userId 이용한 리뷰 전체 조회(페이지) : 성공")
    void getReviewPageByUserIdTest() throws Exception {
        int page = 0;
        int size = 3;
        Page<Review> reviewPage = reviewStub.createPageReview(page, size);
        PageRequest pageInfo = PageRequest.of(0, 3);
        // given
        BDDMockito.given(reviewRepository.findByUserIdPage(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyLong())).willReturn(reviewPage);

        // when
        Page<Review> result = reviewGetService.getReviewPageByUserId(pageInfo, userId);
        // then
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(reviewPage);
        Assertions.assertThat(result.getSize()).isEqualTo(size);

    }

    @Test
    @DisplayName("itemId 이용한 리뷰 전체 조회(페이지) : 성공")
    void getReviewPageByItemIdTest() throws Exception {
        int page = 0;
        int size = 3;
        Page<Review> reviewPage = reviewStub.createPageReview(page, size);
        PageRequest pageInfo = PageRequest.of(0, 3);
        // given
        BDDMockito.given(reviewRepository.findByItemIdPage(ArgumentMatchers.any(Pageable.class), ArgumentMatchers.anyLong())).willReturn(reviewPage);

        // when
        Page<Review> result = reviewGetService.getReviewPageByItemId(pageInfo, itemId);
        // then
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator().isEqualTo(reviewPage);
        Assertions.assertThat(result.getSize()).isEqualTo(size);
    }
}