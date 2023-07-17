package project.main.webstore.domain.item.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.*;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.valueObject.Price;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public Item toEntity(ItemPostDto itemPostDto) {
        if (itemPostDto == null) {
            return null;
        }
        return Item.post().post(itemPostDto).build();
    }

    public Item itemPatchDtoToItem(ItemPatchDto itemPatchDto) {
        if (itemPatchDto == null) {
            return null;
        }
        return Item.patch()
                .patch(itemPatchDto)
                .build();

    }

    // Price Method
    private Price transPrice(Integer price) {
        return Price.builder().value(price).build();
    }

    // itemResponse Mapper
    public ItemResponseDto toGetResponseDto(Item item) {
        return ItemResponseDto.response().item(item).build();
    }

    // itemListResponse Mapper
    public List<ItemResponseDto> itemToItemResponseDtos(List<Item> items) {
        if (items == null) {
            return null;
        }
        List<ItemResponseDto> itemResponseDtos = items.stream().map(item -> {
            ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                    .itemId(item.getItemId())
                    .category(item.getCategory())
                    .name(item.getItemName())
                    .defaultCount(item.getDefaultCount())
                    .description(item.getDescription())
                    .itemPrice(item.getItemPrice().getValue())
                    .deliveryPrice(item.getDeliveryPrice().getValue())

                    .build();

            List<SpecResponseDto> specResponseDtos = item.getSpecList().stream().map(itemSpec -> {
                SpecResponseDto specResponseDto = SpecResponseDto.builder()
                        .specId(itemSpec.getSpecId())
                        .itemName(itemSpec.getItem().getItemName())
                        .content(itemSpec.getContent())
                        .build();
                return specResponseDto;
            }).collect(Collectors.toList());
            itemResponseDto.setSpecList(specResponseDtos);

            List<OptionResponseDto> optionResponseDtos = item.getOptionList().stream().map(itemOption -> {
                OptionResponseDto optionResponseDto = OptionResponseDto.builder()
                        .optionId(itemOption.getOptionId())
                        .optionDetail(itemOption.getOptionDetail())
                        .build();
                return optionResponseDto;
            }).collect(Collectors.toList());
            itemResponseDto.setOptionList(optionResponseDtos);

            return itemResponseDto;
        }).collect(Collectors.toList());

        return itemResponseDtos;
    }

    public ItemIdResponseDto toIdResponse(Item item) {
        return new ItemIdResponseDto(item.getItemId());
    }

    public Page<ItemResponseDto> toGetPageResponse(Page<Item> items) {
        return items.map(ItemResponseDto::new);
    }
}





