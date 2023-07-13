package project.main.webstore.domain.item.dto;

import lombok.Getter;

@Getter
public class SpecGetResponseDto {
    private Long specId;
    private Long itemId;
    private String itemName;
    private String content;

    public SpecGetResponseDto(Long specId, Long itemId, String itemName, String content) {
        this.specId = specId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.content = content;
    }
}
