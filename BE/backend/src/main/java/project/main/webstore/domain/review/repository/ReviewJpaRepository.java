package project.main.webstore.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.main.webstore.domain.review.entity.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewJpaRepository extends JpaRepository<Review,Long>,ReviewRepository {
    @Override
    @Query("select r from Review r where r.user.id = :userId and r.id = :reviewId")
    Optional<Review> findByUserId(@Param("userId") Long userId, @Param("reviewId") Long reviewId);
    @Override
    @Query("select r from Review r where r.user.id = :userId")
    List<Review> findByUserId(@Param("userId") Long userId);
    @Override
    @Query("select r from Review r where r.item.id = :itemId and r.id = :reviewId")
    Optional<Review> findByItemId(@Param("itemId") Long itemId, @Param("reviewId") Long reviewId);
    @Override
    @Query("select r from Review r where r.user.id = :itemId")
    List<Review> findByItemId(@Param("itemId") Long itemId);

    @Override
    @Query(value = "select r from Review r where r.user.id =:userId")
    Page<Review> findByUserIdPage(Pageable pageable, @Param("userId") Long userId);

    @Override
    @Query(value = "select r from Review r where r.user.id =:userId")
    Slice<Review> findByUserIdSlice(Pageable pageable, @Param("userId") Long userId);   //무한 스크롤

    @Override
    @Query("select r from Review r where r.item.id =:itemId")
    Page<Review> findByItemIdPage(Pageable pageable, @Param("itemId") Long itemId);
    @Override
    @Query("select r from Review r where r.item.id =:itemId")
    Slice<Review> findByItemIdSlice(Pageable pageable, @Param("itemId") Long itemId);

    @Override
    @Query("select r from Review r")
    Page<Review> findAllPage(Pageable pageable);

    @Override
    @Query(value = "SELECT r.* FROM review r " +
            "WHERE r.id = :reviewId " +
            "ORDER BY COUNT(r.like) DESC " +
            "LIMIT :count",nativeQuery = true)
    List<Review> findTopN(@Param("reviewId") Long reviewId, @Param("count") int count);
}
