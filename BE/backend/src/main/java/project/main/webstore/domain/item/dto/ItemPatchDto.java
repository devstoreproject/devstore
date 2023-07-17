package project.main.webstore.domain.item.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.item.enums.Category;

import javax.validation.constraints.Pattern;
import java.util.List;

@Getter
@Setter
@Schema(description = "상품 수정 스키마")
public class ItemPatchDto {
    @Schema(example = "COMPUTER",allowableValues = {"COMPUTER", "MONITOR", "MOUSE", "HEADSET", "CHAIR", "DESK"})
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private Category category;
    @Schema(example = "맥북",description = "상품 이름")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9\\s]*$")
    private String name;
    @Pattern(regexp = "^[가-힣a-zA-Z\\d`~!@#$%^&*()-_=+\\s]*$")
    @Schema(description = "상품 상세 설명",example = "수정할 내용은 맥북은 정말 멋있습니다.")
    private String description;
    private int defaultCount;
    @Schema(description = "상품 가격",example = "1000000")
    private Integer itemPrice;
    @Schema(description = "배달 가격",example = "3000")
    private Integer deliveryPrice;


    //이미지 삭제 로직
    @Schema(implementation = Long.class, description = "삭제할 이미지의 ID 리스트")
    private List<Long> deleteImageId;
    @Schema(implementation = ImageSortDto.class,description = "이미지 정렬 및 대표사진 여부 정보 리스트")
    private List<ImageSortDto> imageSortAndRepresentativeInfo;

}
