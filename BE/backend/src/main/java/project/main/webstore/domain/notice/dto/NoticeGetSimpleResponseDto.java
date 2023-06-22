package project.main.webstore.domain.notice.dto;

import lombok.Getter;
import project.main.webstore.domain.notice.entity.Notice;

@Getter
public class NoticeGetSimpleResponseDto {
    private Long noticeId;
    private String title;
    private String content;

    public NoticeGetSimpleResponseDto(Notice notice) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
    }
}
