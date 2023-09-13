package project.main.webstore.domain.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.qna.entity.Question;
import project.main.webstore.domain.qna.enums.QnaStatus;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    @Query("select q from Question q where q.item.itemId = :itemId")
    Page<Question> findAllByItemId(Pageable pageable,@Param("itemId") Long itemId);

    Page<Question> findAllQnaByUserId(Pageable pageable, Long userId);

    @Query("select q from Question  q where q.qnaStatus = :status")
    Page<Question> findByStatus(Pageable pageable,@Param("status")QnaStatus status);
}
