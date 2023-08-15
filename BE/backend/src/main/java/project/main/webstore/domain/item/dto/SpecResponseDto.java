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
    private String specName;
    private String content;

    @Builder
    public SpecResponseDto(Long specId, String specName, String content) {
        this.specId = specId;
        this.specName = specName;
        this.content = content;
    }
    public SpecResponseDto(ItemSpec spec) {
        this.specId = spec.getSpecId();
        this.specName = spec.getName();
        this.content = spec.getContent();
    }


}

