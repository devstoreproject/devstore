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
import project.main.webstore.domain.item.exception.ItemExceptionCode;
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
        if (findReview.getItem().getItemId() != itemId) {
            throw new BusinessLogicException(CommonExceptionCode.ITEM_NOT_FOUND);
        }
        Like like = new Like(findUser, findReview);
        findReview.addLike(like);

        return findReview;
    }

    public Review postReview(Review review) {
        User user = userValidService.validUser(review.getUser().getId());
        Item item = itemService.validItem(review.getItem().getItemId());

        review.addUserAndItem(user, item);

        Review save = reviewRepository.save(review);
        return save;
    }

    public Review postReview(ImageInfoDto imageInfo, Review review) {
        User user = userValidService.validUser(review.getUser().getId());
        Item item = itemService.validItem(review.getItem().getItemId());

        //이미지 저장 로직
        Image image = fileUploader.uploadImage(imageInfo);

        //DB 저장
        ReviewImage reviewImage = new ReviewImage(image, review);
        review.addOrDeleteReviewImage(reviewImage);

        review.addUserAndItem(user, item);

        return reviewRepository.save(review);
    }

    public Review patchReview(ImageInfoDto imageInfo, Review review) {
        Review findReview = reviewValidService.validReview(review.getId());

        //사용자 검증
        if (!findReview.getUser().getId().equals(review.getUser().getId())) {
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_SAME);
        }
        //아이템 검증
        if (!findReview.getItem().getItemId().equals(review.getItem().getItemId())) {
            throw new BusinessLogicException(ItemExceptionCode.ITEM_NOT_SAME);
        }

        //기본적인 정보 존재한다면 변경
        Optional.ofNullable(review.getRating()).ifPresent((findReview::setRating));
        Optional.ofNullable(review.getComment()).ifPresent((findReview::setComment));

        //이미지 수정하는 로직
        Image image = imageUtils.patchImageWithDelete(imageInfo, findReview.getReviewImage());
        ReviewImage reviewImage = createReviewImage(image, findReview);
        findReview.addOrDeleteReviewImage(reviewImage);

        return findReview;
    }

    public Review patchReview(Review review) {
        Review findReview = reviewValidService.validReview(review.getId());

        //사용자 검증
        if (!findReview.getUser().getId().equals(review.getUser().getId())) {
            throw new BusinessLogicException(UserExceptionCode.USER_NOT_SAME);
        }
        //아이템 검증
        if (!findReview.getItem().getItemId().equals(review.getItem().getItemId())) {
            throw new BusinessLogicException(ItemExceptionCode.ITEM_NOT_SAME);
        }

        //기본적인 정보 존재한다면 변경
        Optional.ofNullable(review.getRating()).ifPresent((findReview::setRating));
        Optional.ofNullable(review.getComment()).ifPresent((findReview::setComment));

        return findReview;
    }

    public void deleteReview(Long reviewId,Long userId) {
        Optional<Review> findReview = reviewRepository.findById(reviewId);
        if(findReview.isPresent()){
            Review review = findReview.get();

            userValidService.validUserIdSame(userId, review);

            imageUtils.deleteImage(review.getReviewImage());
            reviewRepository.deleteById(reviewId);
        }
    }

    public List<Review> bestReviewByAdmin(List<Long> reviewIdList) {
        List<Review> reviewList = reviewValidService.validReviewList(reviewIdList);
        reviewList.forEach(review -> review.setBest(true));
        return reviewList;
    }

    public List<Review> deleteBestReview(List<Long> reviewIdList) {
        List<Review> reviewList = reviewValidService.validReviewList(reviewIdList);
        reviewList.forEach(review -> review.setBest(false));

        return reviewList.stream().filter(review -> review.isBest()).collect(Collectors.toList());
    }

    private ReviewImage createReviewImage(Image image, Review review) {
        return image != null ? new ReviewImage(image, review) : null;
    }
}
