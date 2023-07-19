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
public class NoticePatchRequestDto {
    @NotNull
    @Schema(example = "2")
    private Long userId;
    @Schema(example = "수정할 타이틀 명")
    private String title;
    @Schema(example = "수정할 본문 명")
    private String content;
    @Schema(description = "삭제할 이미지 식별자 정보")
    private List<Long> deleteImageId;
    @Schema(description = "이미지 대표값, 순서 정보")
    private List<ImageSortDto> imageSortAndRepresentativeInfo;
}
