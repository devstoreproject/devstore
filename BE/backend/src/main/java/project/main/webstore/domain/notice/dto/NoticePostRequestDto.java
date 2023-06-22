package project.main.webstore.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class NoticePostRequestDto {
    private Long userId;
    private String title;
    private String content;
    private List<ImageSortDto> infoList;


}
