package project.main.webstore.domain.image.dto;

import lombok.Getter;
import project.main.webstore.domain.image.entity.Image;

@Getter
public class ImageDto {
    private Long imageId;
    private String originalPath;
    private String thumbnailPath;
    private boolean isRepresentative;
    private String title;
    private int imageOrder;

    public ImageDto(Image image) {
        this.imageId = image.getId();
        this.originalPath = image.getImagePath();
        this.thumbnailPath = image.getThumbnailPath();
        this.isRepresentative = image.isRepresentative();
        this.title = image.getRealTitle();
        this.imageOrder = image.getImageOrder();
    }
}
