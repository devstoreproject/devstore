package project.main.webstore.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageSortDto {
    private Long id;
    private int orderNumber;
    private boolean representative;

    public ImageSortDto(int orderNumber, boolean representative) {
        this.orderNumber = orderNumber;
        this.representative = representative;
    }
}
