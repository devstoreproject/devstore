package project.main.webstore.domain.notice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;

import java.time.LocalDateTime;

@Getter
public class NoticeGetSimpleResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    private long viewCount;
    private ImageDto image;
    private NoticeCategory category;
    public NoticeGetSimpleResponseDto(Notice notice) {
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
