package project.main.webstore.domain.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageDto;
import project.main.webstore.domain.notice.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NoticeGetResponseDto {
    @Schema(description = "이미지 정보",implementation = ImageDto.class)
    List<ImageDto> imageList;
    private Long noticeId;
    private String title;
    private String content;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    public NoticeGetResponseDto(Notice notice) {
        this.noticeId = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
        this.createAt = notice.getCreatedAt();
        this.modifiedAt = notice.getModifiedAt();
        this.imageList = notice.getNoticeImageList() != null ? notice.getNoticeImageList().stream().map(ImageDto::new).collect(Collectors.toList()) : null;
    }
}
