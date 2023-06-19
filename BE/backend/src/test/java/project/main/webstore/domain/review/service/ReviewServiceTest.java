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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.review.dto.ReviewIdResponseDto;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.domain.review.stub.ReviewStub;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.stub.ImageStub;
import project.main.webstore.utils.FileUploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
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
    private ReviewStub reviewStub = new ReviewStub();
    private ImageStub imageStub = new ImageStub();

    @Test
    @DisplayName("리뷰 작성 : 성공, 이미지 파일 없음")
    void postReviewTest() {
        //given
        ReviewPostRequestDto reviewPostRequestDto = reviewStub.reviewPostRequestDtoNoImage(userId);
        Review findReview = reviewStub.createReview(userId, itemId, reviewId);
        //TODO : user, item 서비스 코드 생성 시 해당 검증 추가 필요
        given(reviewRepository.save(ArgumentMatchers.any(Review.class))).willReturn(findReview);

        //when
        ReviewIdResponseDto expected = reviewService.postReview(reviewPostRequestDto, userId, itemId);
        //then

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(expected.getReviewId()).as("리뷰 Id는 생성되는 리뷰 Id와 일치해야합니다.").isEqualTo(reviewId);
            softAssertions.assertThat(expected.getUserId()).as("사용자 Id는 요청 시 받는 사용자 Id 와 일치 해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(expected.getItemId()).as("아이템 Id는 요청 시 받는 아이템 Id 와 일치 해야합니다.").isEqualTo(itemId);
        });

    }
    @Test
    @DisplayName("리뷰 작성 : 성공, 사진 파일 2개장 존재, ")
    void postReviewAndImageTest() throws IOException {
        String fileName = "testImage";
        String ext = "png";
        String path = "src/test/resources/image/testImage.png";
        FileInputStream fileInputStream1 = new FileInputStream(new File(path));
        FileInputStream fileInputStream2 = new FileInputStream(new File(path));
        List<MultipartFile> fileList = new ArrayList<>();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, fileName.concat(".").concat(ext), ext, fileInputStream1);
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(fileName, fileName.concat(".").concat(ext), ext, fileInputStream2);

        fileList.add(mockMultipartFile);
        fileList.add(mockMultipartFile2);

        ReviewPostRequestDto reviewPostRequestDto = reviewStub.reviewPostRequestDto(userId);
        Review savedReview = reviewStub.createReview(userId, itemId, reviewId);
        given(reviewRepository.save(ArgumentMatchers.any(Review.class))).willReturn(savedReview);
        given(fileUploader.uploadImage(ArgumentMatchers.anyList())).willReturn(imageStub.createImageList(2));

        ReviewIdResponseDto result = reviewService.postReview(reviewPostRequestDto, userId, itemId, fileList);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getReviewId()).as("리뷰 Id는 생성되는 리뷰 Id와 일치해야합니다.").isEqualTo(reviewId);
            softAssertions.assertThat(result.getUserId()).as("사용자 Id는 요청 시 받는 사용자 Id 와 일치 해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(result.getItemId()).as("아이템 Id는 요청 시 받는 아이템 Id 와 일치 해야합니다.").isEqualTo(itemId);
        });
    }


    @Test
    @DisplayName("리뷰 작성 : 예외 발생, 사진과 사진 정렬 정보 가진 리스트의 수가 다를때")
    void postReviewExceptionUserValidTest() throws IOException {
        String fileName = "testImage";
        String ext = "png";
        String path = "src/test/resources/image/testImage.png";
        FileInputStream fileInputStream1 = new FileInputStream(new File(path));
        List<MultipartFile> fileList = new ArrayList<>();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, fileName.concat(".").concat(ext), ext, fileInputStream1);
        fileList.add(mockMultipartFile);

        ReviewPostRequestDto reviewPostRequestDto = reviewStub.reviewPostRequestDto(userId);
        Throwable exception = catchThrowable(() -> reviewService.postReview(reviewPostRequestDto, userId, itemId, fileList));


        Assertions.assertThat(exception).as("저장 파일과 해당 정보의 수를 일치 시키지 않으면 예외가 발생합니다.")
                .isInstanceOf(BusinessLogicException.class)
                .hasMessage("이미지와 정보의 수가 일치하지 않습니다.");
    }
    @Test
    @DisplayName("리뷰 수정 사진 제외 수정 : 성공")
    void updateReviewNoImageTest() throws IOException {
        String fileName = "testImage";
        String ext = "png";
        String path = "src/test/resources/image/testImage.png";
        FileInputStream fileInputStream1 = new FileInputStream(new File(path));
        FileInputStream fileInputStream2 = new FileInputStream(new File(path));
        ReviewUpdateRequestDto requestDto = reviewStub.reviewUpdateRequestDto(userId, true);

        List<MultipartFile> fileList = new ArrayList<>();

        MockMultipartFile mockMultipartFile = new MockMultipartFile(fileName, fileName.concat(".").concat(ext), ext, fileInputStream1);
        MockMultipartFile mockMultipartFile2 = new MockMultipartFile(fileName, fileName.concat(".").concat(ext), ext, fileInputStream2);

        fileList.add(mockMultipartFile);
        fileList.add(mockMultipartFile2);


        Review findReview = reviewStub.createReview(userId, itemId, reviewId);
        List<ReviewImage> reviewImage = imageStub.createReviewImage(findReview);

        given(reviewValidService.validReview(ArgumentMatchers.anyLong())).willReturn(findReview);
        willDoNothing().given(fileUploader).deleteS3Image(ArgumentMatchers.anyString());
        given(fileUploader.uploadImage(ArgumentMatchers.anyList())).willReturn(imageStub.createImageList(2));

        //when
        ReviewIdResponseDto result = reviewService.updateReview(requestDto, fileList, userId, itemId, reviewId);

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getReviewId()).as("리뷰 Id는 생성되는 리뷰 Id와 일치해야합니다.").isEqualTo(reviewId);
            softAssertions.assertThat(result.getUserId()).as("사용자 Id는 요청 시 받는 사용자 Id 와 일치 해야합니다.").isEqualTo(userId);
            softAssertions.assertThat(result.getItemId()).as("아이템 Id는 요청 시 받는 아이템 Id 와 일치 해야합니다.").isEqualTo(itemId);
        });
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