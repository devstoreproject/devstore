package project.main.webstore.domain.notice.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.DefaultMapper;
import project.main.webstore.domain.notice.dto.NoticeGetResponseDto;
import project.main.webstore.domain.notice.dto.NoticeGetSimpleResponseDto;
import project.main.webstore.domain.notice.dto.NoticeIdResponseDto;
import project.main.webstore.domain.notice.dto.NoticePatchRequestDto;
import project.main.webstore.domain.notice.dto.NoticePostRequestDto;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.dto.CustomPage;

@Component
public class NoticeMapper implements DefaultMapper {
    public Notice toEntity(NoticePostRequestDto dto) {
        return new Notice(dto.getTitle(),dto.getContent(),dto.getCategory());
    }

    public Notice toEntity(NoticePatchRequestDto dto) {
        return new Notice(dto.getTitle(),dto.getContent(),dto.getCategory());
    }
    public Notice toEntity(NoticePatchRequestDto dto, Long noticeId){
        if(dto != null)
            return new Notice(noticeId, dto.getTitle(),dto.getContent(),dto.getCategory());
        return new Notice(noticeId);
    }

    public NoticeIdResponseDto toResponseDto(Notice notice) {
        return new NoticeIdResponseDto(notice.getId());
    }

    public CustomPage<NoticeGetSimpleResponseDto> toGetSimplePageResponse (Page<Notice> notice) {
        Page<NoticeGetSimpleResponseDto> map = notice.map(NoticeGetSimpleResponseDto::new);
        return transCustomPage(map);
    }

    public NoticeGetResponseDto toGetRseponseGetDto(Notice notice) {
        return new NoticeGetResponseDto(notice);
    }

}
