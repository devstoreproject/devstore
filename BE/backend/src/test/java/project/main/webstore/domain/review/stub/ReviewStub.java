package project.main.webstore.domain.review.stub;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.image.dto.ImageSortPatchInfo;
import project.main.webstore.domain.image.entity.ReviewImage;
import project.main.webstore.domain.item.entity.Item;
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
                .reviewImageList(new ArrayList<>())
                .rating(10)
                .stubBuild();
    }
    public Review createReview() {
        return Review.stubBuilder()
                .user(new User(1L))
                .item(new Item(1L))
                .id(1L)
                .comment("기본 본문입니다.")
                .reviewImageList(new ArrayList<>())
                .rating(5)
                .stubBuild();
    }
    public Review createReview(Long userId, Long itemId, Long reviewId, List<ReviewImage> reviewImageList) {
        return Review.stubBuilder()
                .user(new User(userId))
                .item(new Item(itemId))
                .id(reviewId)
                .comment("comment는 짧고 간결하게 사용해주시면 감사하겠습니다." + reviewId)
                .reviewImageList(reviewImageList)
                .rating(10)
                .stubBuild();
    }

    public ReviewGetResponseDto reviewGetResponseDto(Review review) {
        return ReviewGetResponseDto.dtoBuilder().review(review).dtoBuild();
    }

    public List<Review> createListReview() {
        List<Review> list = new ArrayList<>();
        for (Long i = 0L; i < 10L; i++) {
            list.add(createReview(i, i, i));
        }
        return list;
    }

    public Page<Review> createPageReview(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return new PageImpl<>(createListReview(), pageRequest, createListReview().size());
    }

    public Page<ReviewGetResponseDto> reviewGetResponseDtoPage(int page, int size) {
        return createPageReview(page, size).map(ReviewGetResponseDto::new);
    }

    public ReviewPostRequestDto reviewPostRequestDtoNoImage(Long userId) {
        return ReviewPostRequestDto.stubBuilder().userId(userId).infoList(null).comment("사진이 없는 리뷰").rating(10).stubBuild();
    }

    public ReviewPostRequestDto reviewPostRequestDto(Long userId,boolean target) {
        return ReviewPostRequestDto.stubBuilder().userId(userId).infoList(imageSortListDto(target)).comment("사진이 없는 리뷰").rating(10).stubBuild();
    }

    public ReviewUpdateRequestDto reviewUpdateRequestDto(Long userId,boolean target) {
        return new ReviewUpdateRequestDto(userId, "이것은 수정입니다.", 5, List.of(1L), imageSortListDto(target));
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
}