package project.main.webstore.domain.notice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.domain.notice.exception.NoticeException;
import project.main.webstore.domain.notice.repository.NoticeRepository;
import project.main.webstore.exception.BusinessLogicException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeGetService {
    private final NoticeRepository repository;

    //단순하게 조회하는 것
    public Notice getNotice(Long noticeId) {
        Notice notice = validNotice(noticeId);
        notice.addViewCount();
        return notice;
    }

    //공지사항 전체 리스트 조회

    public Page<Notice> getSimpleNotice(Pageable pageable,String category) {
            if (category == null) {
                return repository.findNoticePage(pageable);
            }
        NoticeCategory noticeCategory =NoticeCategory.of(category);
        return repository.findNoticePageByCategory(pageable,noticeCategory);
    }
    private Notice validNotice(Long noticeId) {
        return repository.findNotice(noticeId)
                .orElseThrow(() -> new BusinessLogicException(NoticeException.NOTICE_NOT_FOUND));
    }
}
