package project.main.webstore.domain.review.stub;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.review.dto.ReviewGetResponseDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.users.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReviewStub {

    public Review createReview(Long userId, Long itemId, Long reviewId){
        return Review.stubBuilder()
                .user(new User(userId))
                .item(new Item(itemId))
                .id(reviewId)
                .comment("comment는 짧고 간결하게 사용해주시면 감사하겠습니다." + reviewId)
                .reviewImageList(new ArrayList<>())
                .rating(10)
                .stubBuild();

    }
    public ReviewGetResponseDto reviewGetResponseDto(Review review){
        return ReviewGetResponseDto.dtoBuilder().review(review).dtoBuild();
    }

    public List<Review> createListReview() {
        List<Review> list = new ArrayList<>();
        for (Long i = 0L ; i < 10L ; i++){
            list.add(createReview(i,i,i));
        }
        return list;
    }

    public Page<Review> createPageReview(int page ,int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        return new PageImpl<>(createListReview(),pageRequest,createListReview().size());
    }
    public List<ReviewGetResponseDto> reviewGetResponseListDto(List<Review> reviewList){
        return reviewList.stream().map(ReviewGetResponseDto::new).collect(Collectors.toList());
    }
    public Page<ReviewGetResponseDto> reviewGetResponseDtoPage(int page, int size){
        return createPageReview(page,size).map(ReviewGetResponseDto::new);
    }

}