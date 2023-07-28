package project.main.webstore.domain.review.service;

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
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.stub.ImageStub;
import project.main.webstore.utils.FileUploader;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    Long userId = 1L;
    Long itemId = 1L;
    Long reviewId = 1L;
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
        //TODO : user, item 서비스 코드 생성 시 해당 검증 추가 필요
        given(reviewRepository.save(ArgumentMatchers.any(Review.class))).willReturn(findReview);
        given(userValidService.validUser(anyLong())).willReturn(userStub.createUser(userId));
        given(itemService.validItem(anyLong())).willReturn(itemStub.createItem(itemId));
        //when
        Review result = reviewService.postReview(findReview, userId, itemId);
        //then

        SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(result.getUser().getId()).as("review의 User엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
                softAssertions.assertThat(result.getItem().getItemId()).as("review의 Item엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
        });

    }
    @Test
    @DisplayName("리뷰 작성 : 성공, 사진 파일 2개장 존재, ")
    void postReviewAndImageTest() throws IOException {
        List<ImageInfoDto> imageInfoList = imageStub.createImageInfo(1, true);

        Review postReview = reviewStub.createReview(userId, itemId, reviewId);
        List<ReviewImage> reviewImage = imageStub.createReviewImage(postReview);

        Review excepted = reviewStub.createReview(userId, itemId, reviewId,reviewImage);
        given(reviewRepository.save(ArgumentMatchers.any(Review.class))).willReturn(excepted);
        given(fileUploader.uploadImage(ArgumentMatchers.anyList())).willReturn(imageStub.createImageList(2));
        willDoNothing().given(imageUtils).imageValid(imageInfoList);

        Review result = reviewService.postReview(imageInfoList, postReview, userId, itemId);
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getUser().getId()).as("review의 User엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(result.getItem().getItemId()).as("review의 Item엔티티의 id틑 post 받은 것과 동일해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(result.getReviewImageList()).as("저장된 이미지의 정보 일치 여부 체크").isEqualTo(excepted.getReviewImageList());
        });
    }

    @Test
    @DisplayName("리뷰 수정 사진 제외 수정 : 성공")
    void updateReviewNoImageTest(){

    }

    @Test
    @DisplayName("리뷰 수정 [단순하게 일부 사진만 삭제] : 성공")
    void updateReviewTest() {

    }

    @Test
    @DisplayName("리뷰 수정 [단순하게 사진 추가] : 성공")
    void updateReviewAddImageTest() {

    }

    @Test
    @DisplayName("리뷰 수정 [사진 추가 및 삭제] : 성공")
    void updateReviewAddAndDeleteImageTest() {

    }

    @Test
    @DisplayName("리뷰 수정 [사진 수와 요청 사진 정보 불일치] : 성공")
    void updateReviewMisMatchInfoTest() {

    }

    @Test
    @DisplayName("리뷰 수정 : 예외 발생")
    void updateReviewExceptionTest() {
    }

    @Test
    @DisplayName("리뷰 삭제 : 성공")
    void deleteReviewTest() {
    }

    @Test
    @DisplayName("리뷰 삭제 : 예외 발생")
    void deleteReviewExceptionTest() {
    }
}