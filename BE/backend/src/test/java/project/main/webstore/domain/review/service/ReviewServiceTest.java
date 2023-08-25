package project.main.webstore.domain.review.service;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithAnonymousUser;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.stub.ImageStub;
import project.main.webstore.utils.FileUploader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    Long userId = 1L;
    Long itemId = 2L;
    Long reviewId = 3L;
    Long imageId = 4L;
    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewValidService reviewValidService;
    @Mock
    private FileUploader fileUploader;
    @Mock
    private ItemService itemService;
    private ReviewStub reviewStub = new ReviewStub();
    private ImageStub imageStub = new ImageStub();
    private UserStub userStub = new UserStub();
    private ItemStub itemStub = new ItemStub();
    @Mock
    private ImageUtils imageUtils;
    @Mock
    private UserValidService userValidService;

    @Test
    @WithAnonymousUser
    @DisplayName("리뷰 작성 : 성공, 이미지 파일 없음")
    void postReviewTest() {
        //given

        Review findReview = reviewStub.createReview(userId, itemId, reviewId);
        findReview.setUser(userStub.createUser(userId));
        given(reviewRepository.save(ArgumentMatchers.any(Review.class))).willReturn(findReview);
        given(userValidService.validUser(anyLong())).willReturn(userStub.createUser(userId));
        given(itemService.validItem(anyLong())).willReturn(itemStub.createItem(itemId));
        //when
        Review result = reviewService.postReview(findReview);
        //then

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getUser().getId()).as("review의 User엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(result.getItem().getItemId()).as("review의 Item엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(itemId);
        });

    }

    @Test
    @DisplayName("리뷰 작성 : 성공, 사진 파일 존재")
    void postReviewAndImageTest() throws IOException {
        ImageInfoDto imageInfo = imageStub.createImageInfoPath(null, 0, true);
        Review postReview = reviewStub.createReview(userId, itemId, reviewId);
        Image image = imageStub.createImage(1L, 0, true);
        Review excepted = reviewStub.createReview(userId, itemId, reviewId, new ReviewImage(image, postReview));
        given(reviewRepository.save(ArgumentMatchers.any(Review.class))).willReturn(excepted);
        given(fileUploader.uploadImage(ArgumentMatchers.any(ImageInfoDto.class))).willReturn(imageStub.createImage(1L, 0, true));

        Review result = reviewService.postReview(imageInfo, postReview);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getUser().getId()).as("review의 User엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(result.getItem().getItemId()).as("review의 Item엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(itemId);
        });
    }

    @Test
    @DisplayName("리뷰 수정 사진 제외 수정 : 성공")
    void update_review_NoImageTest() {
        Review review = reviewStub.createReview(userId, itemId);
        Review updateReview = reviewStub.createReview(userId, itemId, reviewId);

        given(reviewValidService.validReview(reviewId)).willReturn(review);

        Review result = reviewService.patchReview(updateReview);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getUser().getId()).as("review의 User엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(result.getComment()).as("수정된 질문 본문입니다.").isEqualTo(updateReview.getComment());
        });
    }

    @Test
    @DisplayName("리뷰 수정 comment만 수정: 성공")
    void update_review_only_comment_NoImageTest() {
        Review savedReview = reviewStub.createReview(userId, itemId);
        Review patch = reviewStub.createReviewNoRating(userId, itemId, reviewId);
        String savedComment = savedReview.getComment();

        given(reviewValidService.validReview(reviewId)).willReturn(savedReview);

        Review result = reviewService.patchReview(patch);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(patch.getRating()).as("입력 데이터는 rating가 없어야한다.").isNull();
            softAssertions.assertThat(result.getUser().getId()).as("review의 User엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(savedComment).as("수정된 본문은 기존의 본문과 달라야합니다.").isNotEqualTo(patch.getComment());
            softAssertions.assertThat(savedReview.getComment()).as("comment는 변경되어야한다.").isEqualTo(patch.getComment());
        });
    }

    @Test
    @DisplayName("리뷰 수정 comment만 수정: 성공")
    void update_review_only_rating_NoImageTest() {
        Review savedReview = reviewStub.createReview(userId, itemId);
        Review patch = reviewStub.createReviewNoComment(userId, itemId, reviewId);
        int savedRating = savedReview.getRating();

        given(reviewValidService.validReview(reviewId)).willReturn(savedReview);

        Review result = reviewService.patchReview(patch);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(patch.getComment()).as("입력 데이터는 comment가 없어야한다.").isNull();
            softAssertions.assertThat(result.getUser().getId()).as("review의 User엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(savedRating).as("수정된 rating 은 기존 rating과 달라야한다.").isNotEqualTo(patch.getRating());
            softAssertions.assertThat(savedReview.getRating()).as("수정된 rating 은 입력받은 rating이어야한다.").isEqualTo(patch.getRating());
        });
    }

    @Test
    @DisplayName("리뷰 수정 [사진 변경 포함] : 성공")
    void updateReviewAddImageTest() {
        //given
        Review patch = reviewStub.createReview(userId, itemId, reviewId);
        Review savedReview = reviewStub.createReview(userId, itemId, reviewId);
        ImageInfoDto imageInfo = imageStub.createImageInfoPath(1, true);
        Image resultImage = imageStub.createImage(imageId, 1, true);

        Image image = imageStub.createImage(1L, 1, true);
        ReviewImage reviewImage = new ReviewImage(image, savedReview);
        savedReview.setReviewImage(reviewImage);

        given(reviewValidService.validReview(anyLong())).willReturn(savedReview);
        given(imageUtils.patchImageWithDelete(ArgumentMatchers.any(ImageInfoDto.class), ArgumentMatchers.any(ReviewImage.class))).willReturn(resultImage);

        //when
        Review result = reviewService.patchReview(imageInfo, patch);
        //then
        assertThat(result.getId()).isEqualTo(reviewId);
        assertThat(result.getReviewImage().getId()).as("변경된 ImageId를 가지고 있어야한다.").isEqualTo(imageId);
        assertThat(result.getUser().getId()).isEqualTo(userId);
        assertThat(result.getItem().getItemId()).isEqualTo(itemId);
        assertThat(result.getComment()).isEqualTo(patch.getComment());
        assertThat(result.getRating()).isEqualTo(patch.getRating());
    }

    @Test
    @DisplayName("리뷰 수정 [사진 삭제] : 성공")
    void updateReviewAddAndDeleteImageTest() {
        //give
        Review patch = reviewStub.createReview(userId, itemId, reviewId);
        Review savedReview = reviewStub.createReview(userId, itemId, reviewId);
        ImageInfoDto imageInfo = imageStub.createImageInfoPath(imageId, 1, true);

        Image image = imageStub.createImage(1L, 1, true);
        ReviewImage reviewImage = new ReviewImage(image, savedReview);
        savedReview.setReviewImage(reviewImage);

        given(reviewValidService.validReview(anyLong())).willReturn(savedReview);
        given(imageUtils.patchImageWithDelete(ArgumentMatchers.any(ImageInfoDto.class), ArgumentMatchers.any(ReviewImage.class))).willReturn(null);

        //when
        Review result = reviewService.patchReview(imageInfo, patch);
        //then
        assertThat(result.getId()).isEqualTo(reviewId);
        assertThat(result.getUser().getId()).isEqualTo(userId);
        assertThat(result.getItem().getItemId()).isEqualTo(itemId);
        assertThat(result.getComment()).isEqualTo(patch.getComment());
        assertThat(result.getRating()).isEqualTo(patch.getRating());
        assertThat(result.getReviewImage()).isNull();
    }

    @Test
    @DisplayName("리뷰 수정 : 예외 발생 [사용자 불일치]")
    void patch_review_user_not_same_exception_test() {
        Review savedReview = reviewStub.createReview(userId + 1L, itemId, reviewId);
        Review patch = reviewStub.createReview(userId, itemId, reviewId);

        given(reviewValidService.validReview(anyLong())).willReturn(savedReview);

        Throwable throwable = catchThrowable(() -> reviewService.patchReview(patch));
        Assertions.assertThat(throwable).isInstanceOf(BusinessLogicException.class).hasMessage("회원 불일치");
    }

    @Test
    @DisplayName("리뷰 수정 : 예외 발생 [상품 불일치]")
    void patch_review_item_not_same_exception_test() {
        Review savedReview = reviewStub.createReview(userId, itemId+1, reviewId);
        Review patch = reviewStub.createReview(userId, itemId, reviewId);

        given(reviewValidService.validReview(anyLong())).willReturn(savedReview);

        Throwable throwable = catchThrowable(() -> reviewService.patchReview(patch));
        Assertions.assertThat(throwable).isInstanceOf(BusinessLogicException.class).hasMessage("상품을 찾을 수 없습니다.");
    }

    @Test
    @DisplayName("리뷰 삭제 : 성공")
    void deleteReviewTest() {
        Review savedReview = reviewStub.createReview(userId, itemId, reviewId);


    }
    public void deleteReview(Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);
        ReviewImage reviewImage = findReview.getReviewImage();
        imageUtils.deleteImage(reviewImage);
        reviewRepository.deleteById(reviewId);
    }
}