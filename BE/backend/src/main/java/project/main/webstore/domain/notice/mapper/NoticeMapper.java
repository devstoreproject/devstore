package project.main.webstore.domain.notice.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.notice.dto.*;
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

    public Page<NoticeGetSimpleResponseDto> toGetSimplePageResponse (Page<Notice> notice) {
        return notice.map(NoticeGetSimpleResponseDto::new);
    }

    public NoticeGetResponseDto toGetRseponseGetDto(Notice notice) {
        return new NoticeGetResponseDto(notice);
    }

}
