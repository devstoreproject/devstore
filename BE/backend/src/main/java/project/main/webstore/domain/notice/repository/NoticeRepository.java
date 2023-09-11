package project.main.webstore.domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;

import java.util.Optional;

public interface NoticeRepository {

    Page<Notice> findNoticePage(Pageable pageable);

    @Query("SELECT n FROM Notice n where n.noticeCategory =:category")
    Page<Notice> findNoticePageByCategory(Pageable pageable, @Param("category")NoticeCategory noticeCategory);

    Optional<Notice> findNotice(Long noticeId);

    Notice save(Notice notice);

    void delete(Notice notice);
}
