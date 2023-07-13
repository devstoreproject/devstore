package project.main.webstore.domain.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.main.webstore.domain.item.entity.ItemSpec;

@Getter
@Setter
@NoArgsConstructor
public class SpecResponseDto {
    private Long specId;
    private String itemName;
    private String content;

    @Builder
    public SpecResponseDto(Long specId, String itemName, String content) {
        this.specId = specId;
        this.itemName = itemName;
        this.content = content;
    }
    public SpecResponseDto(ItemSpec spec) {
        this.specId = spec.getSpecId();
        this.itemName = spec.getItemName();
        this.content = spec.getContent();
    }


}

