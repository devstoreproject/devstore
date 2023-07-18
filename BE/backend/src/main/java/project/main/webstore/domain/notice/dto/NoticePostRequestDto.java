package project.main.webstore.domain.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "이미지 대표값, 순서 정보",implementation = ImageSortDto.class)
    private List<ImageSortDto> infoList;


}
