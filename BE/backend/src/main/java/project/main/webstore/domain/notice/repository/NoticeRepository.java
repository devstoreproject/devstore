package project.main.webstore.domain.notice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.main.webstore.domain.notice.entity.Notice;

import java.util.Optional;

public interface NoticeRepository {

    Page<Notice> findNoticePage(Pageable pageable);

    Optional<Notice> findNotice(Long noticeId);

    Notice save(Notice notice);

    void delete(Notice notice);
}
