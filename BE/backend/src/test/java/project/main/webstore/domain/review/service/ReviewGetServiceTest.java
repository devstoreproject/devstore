package project.main.webstore.domain.review.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.List;
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
        Assertions.assertThat(result.getItem().getItemId()).isEqualTo(review.getItem().getItemId());
        Assertions.assertThat(result.getUser().getId()).isEqualTo(review.getUser().getId());
        Assertions.assertThat(result.getId()).as("assertion 수행 이전에 넣어줘야한다.즉 맨 마지막에 나오면 에러 터짐 ㅎㅎ")
                .isEqualTo(reviewId);
    }

    @Test
    @DisplayName("ReviewGetResponseDto  : 실패")
    void getReviewByReviewIdExceptionTest() throws Exception {
        // given
        BDDMockito.given(reviewValidService.validReview(ArgumentMatchers.anyLong()))
                .willThrow(BusinessLogicException.class);
        // when then
        Assertions.assertThatThrownBy(() -> reviewGetService.getReviewByReviewId(reviewId))
                .isInstanceOf(BusinessLogicException.class);
    }

    @Test
    @DisplayName("관리자의 리뷰 전체 조회(페이지) : 성공")
    void getReviewPageTest() throws Exception {
        int page = 0;
        int size = 3;
        Page<Review> reviewPage = reviewStub.createPageReview(page, size);
        PageRequest pageInfo = PageRequest.of(0, 3);
        // given
        BDDMockito.given(reviewRepository.findAllPage(ArgumentMatchers.any(Pageable.class)))
                .willReturn(reviewPage);

        // when
        Page<Review> result = reviewGetService.getReviewPage(pageInfo);
        // then
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(reviewPage);
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
        BDDMockito.given(reviewRepository.findByUserIdPage(ArgumentMatchers.any(Pageable.class),
                ArgumentMatchers.anyLong())).willReturn(reviewPage);

        // when
        Page<Review> result = reviewGetService.getReviewPageByUserId(pageInfo, userId);
        // then
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(reviewPage);
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
        BDDMockito.given(reviewRepository.findByItemIdPage(ArgumentMatchers.any(Pageable.class),
                ArgumentMatchers.anyLong())).willReturn(reviewPage);

        // when
        Page<Review> result = reviewGetService.getReviewPageByItemId(pageInfo, itemId);
        // then
        Assertions.assertThat(result).usingRecursiveFieldByFieldElementComparator()
                .isEqualTo(reviewPage);
        Assertions.assertThat(result.getSize()).isEqualTo(size);
    }

    @Test
    @DisplayName("좋아요 많은 리뷰 조회 테스트")
    void get_best_review_test() throws Exception {
        BDDMockito.given(reviewRepository.findByItemId(anyLong()))
                .willReturn(reviewStub.createList());

        List<Review> result = reviewGetService.getBestReview(1L, 3);

        Assertions.assertThat(result).hasSize(3);
    }

    @Test
    @DisplayName("관리자가 선정한 베스트 리뷰")
    void get_best_review_by_admin_test() throws Exception {
        List<Review> list = reviewStub.createList();
        list.forEach(review -> review.setBest(true));
        BDDMockito.given(reviewRepository.findByAdminBest()).willReturn(list);

        List<Review> result = reviewGetService.getBestReviewByAdmin();

        for (int i = 0; i < result.size(); i++) {
            Assertions.assertThat(result.get(i).isBest()).isTrue();
        }
    }

    @Test
    @DisplayName("좋아요 많은 리뷰 조회 테스트 빈배열")
    void get_best_review_by_admin_empty_test() throws Exception {
        BDDMockito.given(reviewRepository.findByAdminBest()).willReturn(null);

        List<Review> result = reviewGetService.getBestReviewByAdmin();

        Assertions.assertThat(result).isEmpty();
    }
}