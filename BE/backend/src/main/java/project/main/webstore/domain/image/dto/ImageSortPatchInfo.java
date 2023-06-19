package project.main.webstore.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageSortPatchInfo {
    private Long imageId;
    private int orderNumber;
    private boolean representative;
}
