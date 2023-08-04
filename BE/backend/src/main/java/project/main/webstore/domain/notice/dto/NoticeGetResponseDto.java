package project.main.webstore.domain.notice.dto;

import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;

import java.time.LocalDateTime;

@Getter
public class NoticeGetResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private long viewCount;
    private NoticeCategory category;
    private ImageDto image;

    public NoticeGetResponseDto(Notice notice) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createAt = notice.getCreatedAt();
        this.modifiedAt = notice.getModifiedAt();
        this.viewCount = notice.getViewCount();
        this.category = notice.getNoticeCategory();
        this.image = notice.getNoticeImage() != null ? new ImageDto(notice.getNoticeImage()):null;
    }
}
