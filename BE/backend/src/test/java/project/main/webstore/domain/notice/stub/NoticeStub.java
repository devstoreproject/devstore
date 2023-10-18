package project.main.webstore.domain.notice.stub;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.notice.dto.NoticePatchRequestDto;
import project.main.webstore.domain.notice.dto.NoticePostRequestDto;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.helper.TestUtils;

@Component
public class NoticeStub extends TestUtils {
    public Notice createEntity(Long id) {
        return new Notice(id, "타이틀" + id, "컨텐츠" + id, NoticeCategory.EVENT);
    }
    public Notice createEntity(Long id,NoticeCategory noticeCategory) {
        return new Notice(id, "타이틀" + id, "컨텐츠" + id, noticeCategory);
    }

    public Page<Notice> createEntityPage(Long index,NoticeCategory noticeCategory) {
        List<Notice> entityList = createEntityList(index,noticeCategory);

        return new PageImpl<Notice>(entityList,super.getPage(),30);
    }

    private List<Notice> createEntityList(Long index,NoticeCategory noticeCategory) {
        ArrayList<Notice> notices = new ArrayList<>();

        for (Long i = 1L; i <= index; i++) {
            notices.add(createEntity(i,noticeCategory));
        }

        return notices;
    }

    public NoticePostRequestDto createPostDto(Long id) {
        return new NoticePostRequestDto(id, "title" + id, "content" + id, NoticeCategory.EVENT);
    }

    public NoticePatchRequestDto createPatchDto(Long id) {
        return new NoticePatchRequestDto(id, "title" + id, "content" + id, NoticeCategory.EVENT);
    }
}
