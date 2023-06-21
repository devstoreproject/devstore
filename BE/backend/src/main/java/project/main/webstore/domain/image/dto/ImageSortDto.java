package project.main.webstore.domain.image.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ImageSortDto {
    private Long id;
    private int orderNumber;
    private boolean representative;

    public ImageSortDto(int orderNumber, boolean representative) {
        this.orderNumber = orderNumber;
        this.representative = representative;
    }
}
