package project.main.webstore.domain.review.stub;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.image.dto.ImageSortPatchInfo;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.review.dto.ReviewBestRequestDto;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.users.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReviewStub {

    public Review createReview(Long userId, Long itemId, Long reviewId) {
        return Review.stubBuilder()
                .user(new User(userId))
                .item(new Item(itemId))
                .id(reviewId)
                .comment("comment는 짧고 간결하게 사용해주시면 감사하겠습니다." + reviewId)
                .rating(10)
                .stubBuild();
    }
    public Review createReviewNoComment(Long userId, Long itemId, Long reviewId) {
        return Review.stubBuilder()
                .user(new User(userId))
                .item(new Item(itemId))
                .id(reviewId)
                .rating(1)
                .stubBuild();
    }
    public Review createReviewNoRating(Long userId, Long itemId, Long reviewId) {
        return Review.stubBuilder()
                .user(new User(userId))
                .item(new Item(itemId))
                .id(reviewId)
                .comment("comment는 짧고 간결하게 사용해주시면 감사하겠습니다." + reviewId)
                .stubBuild();
    }
    public Review createReview(Long userId, Long itemId) {
        return Review.stubBuilder()
                .user(new User(userId))
                .item(new Item(itemId))
                .id(1L)
                .comment("기본 본문입니다.")
                .reviewImage(null)
                .rating(5)
                .stubBuild();
    }
    public Review createReview(Long userId, Long itemId, Long reviewId, ReviewImage image) {
        return Review.stubBuilder()
                .user(new User(userId))
                .item(new Item(itemId))
                .id(reviewId)
                .comment("comment는 짧고 간결하게 사용해주시면 감사하겠습니다." + reviewId)
                .reviewImage(image)
                .rating(10)
                .stubBuild();
    }

    public ReviewPostRequestDto createReviewPostDto(){
        return new ReviewPostRequestDto("짧고 간결하게 적어봅니다.",10);
    }

    public ReviewGetResponseDto reviewGetResponseDto(Review review) {
        return ReviewGetResponseDto.dtoBuilder().review(review).dtoBuild();
    }

    public List<Review> createList() {
        List<Review> list = new ArrayList<>();
        for (Long i = 1L; i < 10L; i++) {
            list.add(createReview(i, i, i));
        }
        return list;
    }

    public List<Review> createListItemSame(Long itemId) {
        List<Review> list = new ArrayList<>();
        for (Long i = 1L; i < 10L; i++) {
            list.add(createReview(i, itemId, i));
        }
        return list;
    }
    public List<Review> createListUserSame(Long userId) {
        List<Review> list = new ArrayList<>();
        for (Long i = 1L; i < 10L; i++) {
            list.add(createReview(userId, i, i));
        }
        return list;
    }

    public Page<Review> createPageReview(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return new PageImpl<>(createList(), pageRequest, createList().size());
    }
    public Page<Review> createPageReviewItemSame(int page,Long itemId, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return new PageImpl<>(createListItemSame(itemId), pageRequest, createListItemSame(itemId).size());
    }
    public Page<Review> createPageReviewUserSame(int page,Long userId, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return new PageImpl<>(createListUserSame(userId), pageRequest, createListItemSame(userId).size());
    }

    public Page<ReviewGetResponseDto> reviewGetResponseDtoPage(int page, int size) {
        return createPageReview(page, size).map(ReviewGetResponseDto::new);
    }

    public ReviewUpdateRequestDto reviewUpdateRequestDto(Long userId) {
        return new ReviewUpdateRequestDto( "이것은 수정입니다.", 5);
    }

    //이미지단 이동
    public List<ImageSortDto> imageSortListDto(boolean target) {
        return Stream.of(
                imageSortDto(0, false),
                imageSortDto(1, target)
        ).collect(Collectors.toList());
    }

    public ImageSortDto imageSortDto(int order, boolean representative) {
        return new ImageSortDto(order, representative);
    }

    public List<ImageSortPatchInfo> imageSortPatchInfoList(boolean target) {
        return Stream.of(
                new ImageSortPatchInfo(1L,1, false),
                new ImageSortPatchInfo(null,2, false),
                new ImageSortPatchInfo(null, 3,target)
        ).collect(Collectors.toList());
    }

    public ReviewUpdateRequestDto createPatchDto(){
        return new ReviewUpdateRequestDto("수정된 본문입니다.!!", 1);
    }
    public ReviewBestRequestDto createBestRequest(){
        return new ReviewBestRequestDto(List.of(1L,2L));
    }
}