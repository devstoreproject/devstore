package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.utils.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewValidService reviewValidService;
    private final FileUploader fileUploader;

    public Long postReview(ReviewPostRequestDto dto, Long userId, Long itemId, List<MultipartFile> fileList) {
        //TODO : User 검증 및 item 검증 필요
        //TODO: User, Iteme Review와 매핑이 필요하다.
        Review review = new Review(dto);
        for (int i = 0; i < fileList.size(); i++) {
            //저장 로직
            ReviewImage reviewImage = saveImage(dto.getIsRepresentativeList().get(i), fileList.get(i), review, i);
            review.addReviewImage(reviewImage);
        }
        reviewRepository.save(review);
        return review.getId();
    }

    public Long updateReview(ReviewUpdateRequestDto dto, List<MultipartFile> fileList, Long userId, Long itemId) {
        //TODO : 검증을 진행할 떄 userId가 맞는지 검증을 진행할 것 이를 Controller에서 진행할 지 아니면 Service에서 진행할 지 모르겠다.
        Review review = reviewValidService.validReview(dto.getReviewId());
        Optional.ofNullable(dto.getRating()).ifPresent((review::setRating));
        Optional.ofNullable(dto.getComment()).ifPresent((review::setComment));

        if(checkDeleteImage(dto.getDeleteImageId())){
            List<ReviewImage> deleteImage = findImageById(dto.getDeleteImageId(),review);
            List<ReviewImage> reviewImageList = review.getReviewImageList();

            for (int i = 0; i < deleteImage.size(); i++) {
                ReviewImage image = deleteImage.get(i);
                reviewImageList.remove(image);

                Image tmpImage = fileUploader.patchImage(fileList.get(i),image.getImagePath(),"review",image.getImageOrder(),image.isRepresentative());
                ReviewImage reviewImage = getReviewImage(review, tmpImage);
                review.addReviewImage(reviewImage);
            }
        }

        return review.getId();
    }

    public void deleteReview(Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);
        List<String> pathList = findReview.getReviewImageList().stream().map(ReviewImage::getImagePath).collect(Collectors.toList());
        deleteImage(pathList);
        reviewRepository.deleteById(reviewId);
    }

    public void deleteAll() {
        reviewRepository.deleteAll();
    }


    // #### 내부 동작 메서드 #### //
    //사진 저장
    private ReviewImage saveImage(Boolean isRepresentative, MultipartFile uploadFile, Review review, int order) {
        Image image = fileUploader.uploadImage(uploadFile, "review",order,isRepresentative);
        ReviewImage reviewImage = getReviewImage(review, image);
        return reviewImage;
    }
    //리뷰이미지 생성
    private boolean checkDeleteImage(List<Long> deleteImageIdList) {
        return !deleteImageIdList.isEmpty();
    }

    private List<ReviewImage> findImageById(List<Long> deleteImageId, Review review) {
        List<ReviewImage> reviewImage = review.getReviewImageList().stream().filter(image -> deleteImageId.contains(image.getId())).collect(Collectors.toList());
        return reviewImage;
    }

    private ReviewImage getReviewImage(Review review, Image image) {
        ReviewImage reviewImage = new ReviewImage(image, review);
        return reviewImage;
    }

    private void deleteImage(List<String> deletePath) {
        for (String path : deletePath) {
            fileUploader.deleteS3Image(path);
        }
    }

}
