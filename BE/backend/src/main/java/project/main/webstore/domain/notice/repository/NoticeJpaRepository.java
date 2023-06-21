package project.main.webstore.domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.notice.entity.Notice;

import java.util.Optional;

public interface NoticeJpaRepository extends JpaRepository<Notice, Long>, NoticeRepository {
    @Override
    @Query("select n from Notice n")
    Page<Notice> findNoticePage(Pageable pageable);

    @Override
    @Query("select n from Notice n where n.id = :noticeId")
    Optional<Notice> findNotice(@Param("noticeId") Long noticeId);


}
