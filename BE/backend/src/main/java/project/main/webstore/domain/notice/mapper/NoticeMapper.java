package project.main.webstore.domain.notice.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.notice.dto.NoticeIdResponseDto;
import project.main.webstore.domain.notice.dto.NoticePatchRequestDto;
import project.main.webstore.domain.notice.dto.NoticePostRequestDto;
import project.main.webstore.domain.notice.entity.Notice;

@Component
public class NoticeMapper {
    public Notice toEntity(NoticePostRequestDto dto) {
        return new Notice(dto.getTitle(),dto.getContent());
    }

    public Notice toEntity(NoticePatchRequestDto dto) {
        return new Notice(dto.getTitle(),dto.getContent());
    }
    public Notice toEntity(NoticePatchRequestDto dto, Long noticeId){
        return new Notice(noticeId, dto.getTitle(),dto.getContent());
    }

    public NoticeIdResponseDto toResponseDto(Notice notice) {
        return new NoticeIdResponseDto(notice.getId());
    }
}
