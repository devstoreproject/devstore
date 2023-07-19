package project.main.webstore.domain.notice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.main.webstore.domain.image.dto.ImageSortDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoticePostRequestDto {
    @Schema(example = "2")
    private Long userId;
    @Schema(example = "공지 타이틀")
    @NotNull
    private String title;
    @NotNull
    @Schema(example = "공지 본문")
    private String content;
    @Schema(description = "이미지 대표값, 순서 정보")
    private List<ImageSortDto> infoList;


}
