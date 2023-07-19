package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
public class PickedItemDto {
    private Long userId;
    private Long itemId;
    @Setter
    @Schema(description = "상품 찜 여부 반환",allowableValues = {"true","false"})
    private Boolean picked;

    public PickedItemDto(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }
}
