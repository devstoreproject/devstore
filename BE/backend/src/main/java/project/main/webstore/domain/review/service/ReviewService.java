package project.main.webstore.domain.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.review.dto.ReviewPostRequestDto;
import project.main.webstore.domain.review.dto.ReviewUpdateRequestDto;
import project.main.webstore.domain.review.entity.Review;
import project.main.webstore.domain.review.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ReviewValidService reviewValidService;
    public Long postReview(ReviewPostRequestDto dto,Long userId, Long itemId){
        //TODO : User 검증 및 item 검증 필요
        //TODO : 검증 시 만들어진 User item 엔티티 이용해서 값을 등록한다.
        List<String> imagePathList = dto.getImagePathList();

        //TODO: Image 저장하는 로직 구현 필요

        Review review = new Review(dto);
        reviewRepository.save(review);
        return review.getId();
    }

    public Long updateReview(ReviewUpdateRequestDto dto, Long userId, Long itemId){
        //TODO : 검증을 진행할 떄 userId가 맞는지 검증을 진행할 것 이를 Controller에서 진행할 지 아니면 Service에서 진행할 지 모르겠다.
        //Controller에서 진행할 경우 AOP를 이용해서 진행할 예정이다.

        //TODO: 사진 저장 시 사진이 있다면 사진 검증을 진행한다.

        Review review = reviewValidService.validReview(dto.getReviewId());
        Optional.ofNullable(dto.getRating()).ifPresent((review::setRating));
        Optional.ofNullable(dto.getComment()).ifPresent((review::setComment));

        //TODO 사진을 저장하는 로직이 필요하다.


        return review.getId();
    }

    public void deleteReview(Long reviewId){
        reviewRepository.deleteById(reviewId);
    }

    public void deleteAll(){
        reviewRepository.deleteAll();
    }

    private boolean checkImage() {

        return false;
    }
}
