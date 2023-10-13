package project.main.webstore.domain.notice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.willDoNothing;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.NoticeImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.domain.notice.repository.NoticeRepository;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.stub.ImageStub;
import project.main.webstore.utils.FileUploader;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

    @InjectMocks
    NoticeService service;
    @Mock
    NoticeRepository repository;

    @Mock
    FileUploader fileUploader;
    @Mock
    ImageUtils imageUtils;
    @Mock
    NoticeGetService noticeGetService;
    @Mock
    UserValidService userValidService;
    ImageStub imageStub = new ImageStub();
    UserStub userStub = new UserStub();

    @Test
    @DisplayName("공지 등록 : 이미지 없음")
    void post_notice_test() throws Exception {
        // given
        User user = userStub.createUser(1L);
        Notice notice = new Notice(1L, "타이틀", "컨텐츠", NoticeCategory.EVENT);

        given(userValidService.validUser(anyLong())).willReturn(user);
        given(repository.save(any(Notice.class))).willReturn(notice);
        // when
        Notice result = service.postNotice(notice, 1L);
        // then
        Assertions.assertThat(result.getNoticeCategory()).isEqualTo(NoticeCategory.EVENT);
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getUser().getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("공지 등록 : 이미지")
    void post_notice_with_image_test() throws Exception {
        // given
        User user = userStub.createUser(1L);
        Image image = imageStub.createImage(1, true);
        Notice notice = new Notice(1L, "타이틀", "컨텐츠", NoticeCategory.EVENT);
        notice.setNoticeImage(new NoticeImage(image, notice));

        given(userValidService.validUser(anyLong())).willReturn(user);
        given(repository.save(any(Notice.class))).willReturn(notice);
        given(fileUploader.uploadImage(any(ImageInfoDto.class))).willReturn(image);
        // when
        Notice result = service.postNotice(notice, 1L);
        // then
        Assertions.assertThat(result.getNoticeCategory()).isEqualTo(NoticeCategory.EVENT);
        Assertions.assertThat(result.getId()).isEqualTo(1L);
        Assertions.assertThat(result.getUser().getId()).isEqualTo(1L);
        Assertions.assertThat(result.getNoticeImage().getOriginalName()).isEqualTo("testImage1");
    }

    @Test
    @DisplayName("공지 사항 삭제")
    void delete_notice_test() throws Exception {
        // given
        Notice notice = new Notice(1L, "타이틀", "컨텐츠", NoticeCategory.EVENT);
        notice.setNoticeImage(new NoticeImage());

        given(noticeGetService.getNotice(anyLong())).willReturn(notice);
        willDoNothing().given(imageUtils).deleteImage(any(NoticeImage.class));
        willDoNothing().given(repository).delete(any(Notice.class));
        // when

        service.deleteNotice(1L);
        // then
        verify(noticeGetService, times(1)).getNotice(1L);
        verify(imageUtils, times(1)).deleteImage(notice.getNoticeImage());
        verify(repository, times(1)).delete(notice);

    }

}