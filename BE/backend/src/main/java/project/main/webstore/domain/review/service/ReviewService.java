package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.like.entity.Like;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;
import project.main.webstore.utils.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewValidService reviewValidService;
    private final FileUploader fileUploader;

    private static void imageValid(List<ImageInfoDto> imageInfoList) {
        List<ImageInfoDto> checkRepresentative = imageInfoList.stream().filter(info -> info.isRepresentative()).collect(Collectors.toList());
        if (checkRepresentative.size() != 1) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_HAS_ALWAYS_REPRESENTATIVE);
        }
        boolean check = imageInfoList.stream().map(ImageInfoDto::getOrder).distinct().count() != imageInfoList.size();
        if (check) {
            throw new BusinessLogicException(CommonExceptionCode.IMAGE_ORDER_ALWAYS_UNIQUE);
        }
    }

    public Review addLikeReview(Long reviewId, Long itemId, Long userId) {
        //TODO: item, user 검증 먼저 진행할 필요 존재
        Review findReview = reviewValidService.validReview(reviewId);
        Like like = new Like(findReview);
        findReview.addLike(like);

        return findReview;
    }

    public Review postReview(Review review, Long userId, Long itemId) {
        //TODO : User 검증 및 item 검증 필요
        //TODO: User, Iteme Review와 매핑이 필요하다.
        Review savedReview = reviewRepository.save(review);


        return savedReview;
    }

    public Review postReview(List<ImageInfoDto> imageInfoList, Review review, Long userId, Long itemId) {
        //TODO : User 검증 및 item 검증 필요

//      TODO : 서비스단에서 체크
//        if (imageSortInfoList.size() != fileList.size()) {
//            throw new BusinessLogicException(CommonExceptionCode.IMAGE_INFO_COUNT_MISMATCH);
//        }

        //TODO:메서드 따로 뺴야함
        imageValid(imageInfoList);

        //이미지 저장 로직
        List<Image> imageList = fileUploader.uploadImage(imageInfoList);
        //이미지 DB 저장 로직
        imageList.stream().map(image -> new ReviewImage(image, review)).forEach(review::addReviewImage);
        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }

    public Review patchReview(List<ImageInfoDto> imageInfoList, List<Long> deleteIdList, Review review, Long userId, Long itemId, Long reviewId) {
        //TODO: 연관관계 찾기
        Review findReview = reviewValidService.validReview(reviewId);
        //기본적인 정보 존재한다면 변경
        Optional.ofNullable(review.getRating()).ifPresent((findReview::setRating));
        Optional.ofNullable(review.getComment()).ifPresent((findReview::setComment));


        //TODO:메서드 따로 뺴야함
        imageValid(imageInfoList);


        patchImage(imageInfoList, findReview, deleteIdList);

        return findReview;
    }

    public void deleteReview(Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);
        List<String> pathList = findReview.getReviewImageList().stream().map(ReviewImage::getImagePath).collect(Collectors.toList());
        deleteImage(pathList);
        reviewRepository.deleteById(reviewId);
    }


    // #### 내부 동작 메서드 #### //
    //사진 저장
    private void patchImage(List<ImageInfoDto> infoList, Review review, List<Long> deleteIdList) {
        List<ReviewImage> imageList = review.getReviewImageList();

        if (infoList.isEmpty() == false) {
            List<ImageInfoDto> addImageList = infoList.stream().filter(info -> info.getId() == null).collect(Collectors.toList());
            List<ImageInfoDto> savedImageList = infoList.stream().filter(info -> info.getId() != null).collect(Collectors.toList());

            log.info("#### add{}",addImageList);
            log.info("#### saved{}",savedImageList);


            changeRepresentativeAndOrder(savedImageList, imageList);


            if (deleteIdList != null) {
                //사진 삭제하는 경우
                List<ReviewImage> deleteImage = findImageById(deleteIdList, review);
                List<String> deleteImagePath = deleteImage.stream().map(Image::getImagePath).collect(Collectors.toList());

                deleteImageList(imageList, deleteIdList, deleteImagePath);
            }
            List<Image> uploadedImageList = fileUploader.uploadImage(addImageList);
            uploadedImageList.stream().map(image -> new ReviewImage(image, review)).forEach(review::addReviewImage);
        }

    }

    private void changeRepresentativeAndOrder(List<ImageInfoDto> requestInfoList, List<ReviewImage> imageList) {
        for (int i = 0; i < requestInfoList.size(); i++) {
            ImageInfoDto requestInfo = requestInfoList.get(i);

            Optional<ReviewImage> first = imageList.stream().filter(image -> image.getId() == requestInfo.getId()).findFirst();

            ReviewImage image = first.orElseThrow(() -> new BusinessLogicException(CommonExceptionCode.IMAGE_NOT_FOUND));

            log.info("### ReviewImage = {}",image);

            image.setRepresentative(requestInfo.isRepresentative());
            image.setImageOrder(requestInfo.getOrder());
        }
    }

    private void deleteImageList(List<ReviewImage> reviewImageList, List<Long> deleteId, List<String> deleteImagePath) {
        for (int i = 0; i < deleteId.size(); i++) {
            Long id = deleteId.get(i);
            int index;
            for (index = 0; index < reviewImageList.size(); index++) {
                if (reviewImageList.get(index).getId() == id) {
                    break;
                }
            }
            reviewImageList.remove(index);
            deleteImage(deleteImagePath);
        }
    }

    private List<ReviewImage> findImageById(List<Long> deleteImageId, Review review) {
        List<ReviewImage> reviewImage = review.getReviewImageList().stream().filter(image -> deleteImageId.contains(image.getId())).collect(Collectors.toList());
        return reviewImage;
    }

    private void deleteImage(List<String> deletePath) {
        for (String path : deletePath) {
            fileUploader.deleteS3Image(path);
        }
    }
}