package project.main.webstore.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageSortPatchDto implements ImageSortDto{
    @Schema(example = "1")
    private Long imageId;
    @Schema(example = "1")
    private int orderNumber;
    @Schema(example = "true")
    private boolean representative;

    @Override
    public Long findImageId() {
        return imageId;
    }
}
