package project.main.webstore.domain.qna.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.main.webstore.domain.qna.entity.Answer;

public interface AnswerRepository extends JpaRepository<Answer,Long> {

}
