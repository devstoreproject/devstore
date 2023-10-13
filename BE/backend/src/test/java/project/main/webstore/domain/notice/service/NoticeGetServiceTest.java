package project.main.webstore.domain.notice.service;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.domain.notice.exception.NoticeException;
import project.main.webstore.domain.notice.repository.NoticeRepository;
import project.main.webstore.domain.notice.stub.NoticeStub;
import project.main.webstore.exception.BusinessLogicException;

@ExtendWith(MockitoExtension.class)
class NoticeGetServiceTest {
    @InjectMocks
    NoticeGetService getService;
    @Mock
    NoticeRepository repository;

    NoticeStub noticeStub = new NoticeStub();

    @Test
    @DisplayName("상품 단건 조회")
    void get_notice_test() throws Exception{
        // given
        long noticeId = 1L;
        Notice getMockEntity = noticeStub.createEntity(noticeId);

        given(repository.findNotice(anyLong())).willReturn(Optional.of(getMockEntity));
        // when
        Notice result = getService.getNotice(noticeId);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(getMockEntity);
    }

    @Test
    @DisplayName("상품 단건 조회 : 실패")
    void get_notice_fail_test() throws Exception{
        // given
        long noticeId = 1L;
        Notice getMockEntity = noticeStub.createEntity(noticeId);

        given(repository.findNotice(anyLong())).willReturn(Optional.empty());
        // when
        Assertions.assertThatThrownBy(() -> getService.getNotice(noticeId)).isInstanceOf(
                BusinessLogicException.class).hasMessage(NoticeException.NOTICE_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("상품 전체 조회 : 카테고리 존재 시")
    void get_notice_all_by_category_test() throws Exception{
        // given
        Pageable page = noticeStub.getPage();
        String category = "EVENT";

        Page<Notice> getMockPageEntity = noticeStub.createEntityPage(20L, NoticeCategory.EVENT);
        given( repository.findNoticePageByCategory(any(Pageable.class),any(NoticeCategory.class))).willReturn(
                getMockPageEntity);
        // when
        Page<Notice> result = getService.getSimpleNotice(page, category);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(getMockPageEntity);
    }
    @Test
    @DisplayName("상품 전체 조회 : 카테고리 존재 시")
    void get_notice_all_test() throws Exception{
        // given
        Pageable page = noticeStub.getPage();

        Page<Notice> getMockPageEntity = noticeStub.createEntityPage(20L, NoticeCategory.EVENT);
        given( repository.findNoticePage(any(Pageable.class))).willReturn(getMockPageEntity);
        // when
        Page<Notice> result = getService.getSimpleNotice(page,null);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(getMockPageEntity);
    }

}