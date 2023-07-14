package project.main.webstore.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Schema(name = "저장 및 수정 이미지 정보")
@NoArgsConstructor
public class ImageSortDto {
    @Schema(name = "Image Id",description = "이미지 식별자, 단순 저장할 때는 null값 제공, \n patch 시 새로 저장할 이미지와 저장했었던 이미지 구분을 위한 정보",nullable = true)
    private Long imageId;
    @Schema(name = "이미지 순서",description = "이미지 순서")
    private int orderNumber;
    @Schema(name = "이미지 대표값여부",description = "이미지 대표값 여부")
    private boolean representative;

    public ImageSortDto(int orderNumber, boolean representative) {
        this.orderNumber = orderNumber;
        this.representative = representative;
    }
}
