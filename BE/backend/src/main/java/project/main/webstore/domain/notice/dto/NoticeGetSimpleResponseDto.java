package project.main.webstore.domain.notice.dto;

import lombok.Getter;
import project.main.webstore.domain.notice.entity.Notice;

import java.time.LocalDateTime;

@Getter
public class NoticeGetSimpleResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    public NoticeGetSimpleResponseDto(Notice notice) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createAt = notice.getCreatedAt();
        this.modifiedAt = notice.getModifiedAt();
    }
}
