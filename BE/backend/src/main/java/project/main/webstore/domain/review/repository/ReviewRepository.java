package project.main.webstore.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import project.main.webstore.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Optional<Review> findById(Long reviewId);
    Optional<Review> findByUserId(Long userId, Long reviewId);
    Optional<Review> findByItemId(Long itemId, Long reviewId);

    List<Review> findByUserId(Long userId);
    List<Review> findByItemId(Long itemId);
    Page<Review> findByUserIdPage(Pageable pageable ,Long userId);
    Page<Review> findByItemIdPage(Pageable pageable, Long itemId);
    Slice<Review> findByUserIdSlice(Pageable pageable, Long userId);
    Slice<Review> findByItemIdSlice(Pageable pageable, Long itemId);
    Review save(Review review);
    List<Review> findAll();
    void deleteAll();
    void deleteById(Long id);
}
