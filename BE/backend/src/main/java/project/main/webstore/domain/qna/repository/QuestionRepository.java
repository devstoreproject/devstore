package project.main.webstore.domain.qna.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.main.webstore.domain.qna.entity.Question;

public interface QuestionRepository extends JpaRepository<Question,Long> {

    Page<Question> findAllByItemId(Pageable pageable, Long itemId);

    Page<Question> findAllQnaByUserId(Pageable pageable, Long userId);

    @Query("select q from Question  q where q.qnaStatus = project.main.webstore.domain.qna.enums.QnaStatus.REGISTER")
    Page<Question> findByStatus(Pageable pageable);
}
