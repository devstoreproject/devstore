package project.main.webstore.domain.item.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SpecResponseDto {
    private Long specId;
    private String itemName;
    private String content;

    @Builder

    public SpecResponseDto(Long specId, String itemName, String content) {
        this.specId   = specId;
        this.itemName = itemName;
        this.content  = content;
    }
    //    public SpecResponseDto(Spec spec) {
//        this.specId = spec.getSpecId();
//        this.itemName = spec.getItem().getItemName();
//        this.content = spec.getContent();
//    }
}
