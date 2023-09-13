package project.main.webstore.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Schema(description = "저장 및 수정 이미지 정보")
@NoArgsConstructor
public class ImageSortPostDto implements ImageSortDto{
    @Schema(description = "이미지 순서",example = "2")
    @NotNull
    private int orderNumber;
    @NotNull
    @Schema(description = "이미지 대표값 여부",example = "true")
    private boolean representative;

    public ImageSortPostDto(int orderNumber, boolean representative) {
        this.orderNumber = orderNumber;
        this.representative = representative;
    }

    @Override
    public Long findImageId() {
        return null;
    }

}
