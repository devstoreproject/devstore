package project.main.webstore.domain.notice.dto;

import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.notice.entity.Notice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NoticeGetResponseDto {
    List<ImageDto> imageList;
    private Long noticeId;
    private String title;
    private String content;

    public NoticeGetResponseDto(Notice notice) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.imageList = notice.getNoticeImageList() != null ? notice.getNoticeImageList().stream().map(ImageDto::new).collect(Collectors.toList()) : null;
    }
}
