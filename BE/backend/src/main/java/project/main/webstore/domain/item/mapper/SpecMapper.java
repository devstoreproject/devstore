package project.main.webstore.domain.item.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.SpecGetResponseDto;
import project.main.webstore.domain.item.dto.SpecPatchDto;
import project.main.webstore.domain.item.dto.SpecPostDto;
import project.main.webstore.domain.item.entity.ItemSpec;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpecMapper {
    // itemSpec Mapper
    public ItemSpec specPostDtoToSpec(SpecPostDto specPostDto) {
        return ItemSpec.builder()
                .content(specPostDto.getContent())
                .build();
    }

    public ItemSpec specPatchDtoToSpec(SpecPatchDto specpatchDto) {
        return ItemSpec.builder()
                .content(specpatchDto.getContent())
                .itemName(specpatchDto.getItemName())
                .build();
    }

    public ItemSpec specToSpecResponse(ItemSpec itemSpec) {
        return ItemSpec.builder()
                .specId(itemSpec.getSpecId())
                .itemName(itemSpec.getItemName())
                .content(itemSpec.getContent())
                .build();
    }

    public SpecGetResponseDto toGetResponse(ItemSpec spec) {
        return new SpecGetResponseDto(spec.getSpecId(),spec.getItem().getItemId(),spec.getItemName(),spec.getContent());
    }

    public List<SpecGetResponseDto> toGetResponseList(List<ItemSpec> result) {
        return result.stream().map(this::toGetResponse).collect(Collectors.toList());
    }
}
