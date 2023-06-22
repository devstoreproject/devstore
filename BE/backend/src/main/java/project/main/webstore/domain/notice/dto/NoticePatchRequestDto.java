package project.main.webstore.domain.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.main.webstore.domain.image.dto.ImageSortDto;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@AllArgsConstructor
public class NoticePatchRequestDto {
    @NotNull
    private Long userId;
    private String title;
    private String content;
    private List<Long> deleteImageId;
    private List<ImageSortDto> imageSortAndRepresentativeInfo;
}
