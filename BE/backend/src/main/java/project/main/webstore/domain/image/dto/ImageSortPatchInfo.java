package project.main.webstore.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageSortPatchInfo {
    @Schema(description = "이미지 식별자 \n post 시 null 값으로 던짐")
    private Long imageId;
    private int orderNumber;
    private boolean representative;
}
