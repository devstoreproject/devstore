package project.main.webstore.domain.image.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import project.main.webstore.domain.image.entity.Image;

@Getter
public class ImageDto {
    @Schema(description = "Image Id")
    private Long imageId;
    @Schema(description = "Original Image Path" ,example = "https://item4.blog/2017-12-15/Why-You-Can-Not-Make-Program/facebook.png")
    private String originalPath;
    @Schema(description = "Thumbnail Image Path",example = "https://item4.blog/2017-12-15/Why-You-Can-Not-Make-Program/facebook.png")
    private String thumbnailPath;
    @Schema(description = "대표 사진 여부")
    private boolean isRepresentative;
    @Schema(description = "사진 타이틀")
    private String title;
    @Schema(description = "이미지 순서")
    private int imageOrder;

    public ImageDto(Image image) {
        this.imageId = image.getId();
        this.originalPath = image.getImagePath();
        this.thumbnailPath = image.getThumbnailPath();
        this.isRepresentative = image.isRepresentative();
        this.title = image.getOriginalName();
        this.imageOrder = image.getImageOrder();
    }
}
