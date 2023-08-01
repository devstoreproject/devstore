package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.like.entity.Like;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.exception.UserExceptionCode;
import project.main.webstore.domain.users.service.UserValidService;
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
    private final ImageUtils imageUtils;
    private final ItemService itemService;
    private final UserValidService userValidService;
    public Review addLikeReview(Long reviewId, Long itemId, Long userId) {
        User findUser = userValidService.validUser(userId);
        Review findReview = reviewValidService.validReview(reviewId);
        if(findReview.getItem().getItemId() != itemId){
            throw new BusinessLogicException(CommonExceptionCode.ITEM_NOT_FOUND);
        }
        Like like = new Like(findUser,findReview);
        findReview.addLike(like);

        return findReview;
    }

    public Review postReview(Review review, Long userId, Long itemId) {
        User user = userValidService.validUser(userId);
        Item item = itemService.validItem(itemId);
        review.setUser(user);
        review.setItem(item);
        Review savedReview = reviewRepository.save(review);


        return savedReview;
    }

    public Review postReview(List<ImageInfoDto> imageInfoList, Review review, Long userId, Long itemId) {
        imageUtils.imageValid(imageInfoList);
        //이미지 저장 로직
        List<Image> imageList = fileUploader.uploadImage(imageInfoList);
        //이미지 DB 저장 로직
        imageList.stream().map(image -> new ReviewImage(image, review)).forEach(review::addReviewImage);

        User user = userValidService.validUser(userId);
        Item item = itemService.validItem(itemId);
        review.setUser(user);
        review.setItem(item);
        Review savedReview = reviewRepository.save(review);
        return savedReview;
    }

    public Review patchReview(List<ImageInfoDto> imageInfoList, List<Long> deleteIdList, Review review, Long userId, Long itemId, Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);

        //사용자 검증
        if(!findReview.getUser().getId().equals(userId)){
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_SAME);
        }
        //아이템 검증
        if(!findReview.getItem().getItemId().equals(itemId)){
            //TODO: ItemExcpetion 미구현 -> 작업 진행 이후 변경 예정
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_SAME);
        }

        //기본적인 정보 존재한다면 변경
        Optional.ofNullable(review.getRating()).ifPresent((findReview::setRating));
        Optional.ofNullable(review.getComment()).ifPresent((findReview::setComment));

        if(imageInfoList != null){
            imageUtils.imageValid(imageInfoList);
            List<Image> imageList = imageUtils.patchImage(imageInfoList, findReview.getReviewImageList(), deleteIdList);
            if (imageList.isEmpty() == false) {
                imageList.stream().map(image -> new ReviewImage(image, review)).forEach(findReview::addReviewImage);
            }
        }
        return findReview;
    }

    public void deleteReview(Long reviewId) {
        Review findReview = reviewValidService.validReview(reviewId);
        List<String> pathList = findReview.getReviewImageList().stream().map(ReviewImage::getImagePath).collect(Collectors.toList());
        deleteImage(pathList);
        reviewRepository.deleteById(reviewId);
    }

    public List<Review> bestReviewByAdmin(List<Long> reviewIdList){
        List<Review> reviewList = reviewValidService.validReviewList(reviewIdList);
        reviewList.forEach(review -> review.setBest(true));
        return reviewList;
    }
    public List<Review> deleteBestReview(List<Long> reviewIdList){
        List<Review> reviewList = reviewValidService.validReviewList(reviewIdList);
        reviewList.forEach(review -> review.setBest(false));

        return reviewList.stream().filter(review->review.isBest()).collect(Collectors.toList());
    }
    private void deleteImage(List<String> deletePath) {
        for (String path : deletePath) {
            fileUploader.deleteS3Image(path);
        }
    }
}
