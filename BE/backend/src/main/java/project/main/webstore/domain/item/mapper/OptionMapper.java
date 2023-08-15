package project.main.webstore.domain.item.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.*;
import project.main.webstore.domain.item.entity.ItemOption;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OptionMapper {

    public OptionResponseDto optionToGetResponse(ItemOption option) {
        return new OptionResponseDto(option);
    }

    public List<OptionResponseDto> optionToGetListResponse(List<ItemOption> optionList) {
        return optionList.stream().map(option -> new OptionResponseDto(option)).collect(Collectors.toList());
    }

    public ItemOption optionPostDtoToOption(OptionPostDto optionPostDto) {
        return ItemOption.builder()
                .optionDetail(optionPostDto.getOptionDetail())
                .build();
    }

    public ItemOption toEntity(OptionPatchDto optionPatchDto) {
        return ItemOption.builder()
                .optionDetail(optionPatchDto.getOptionDetail())
                .itemCount(optionPatchDto.getItemCount())
                .build();
    }

    public ItemOption optionResponseToOption(ItemOption itemOption) {
        return ItemOption.builder()
                .optionId(itemOption.getOptionId())
                .optionDetail(itemOption.getOptionDetail())
                .build();
    }

    public ItemOption toEntity(OptionPostRequestDto post) {
        return new ItemOption(post.getOptionDetail(),post.getItemCount(),post.getAdditionalPrice(),post.getOptionName());
    }

    public OptionIdResponseDto toOptionIdResponse(ItemOption result) {
        return new OptionIdResponseDto(result.getItem().getItemId(),result.getOptionId());
    }
}
