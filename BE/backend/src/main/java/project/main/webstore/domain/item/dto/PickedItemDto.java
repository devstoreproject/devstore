package project.main.webstore.domain.item.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PickedItemDto {
    private Long userId;
    private Long itemId;
    @Setter
    private Boolean picked;

    public PickedItemDto(Long userId, Long itemId) {
        this.userId = userId;
        this.itemId = itemId;
    }
}
