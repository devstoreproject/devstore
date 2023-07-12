package project.main.webstore.domain.item.mapper;

import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.*;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.entity.ItemSpec;
import project.main.webstore.valueObject.Price;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public Item itemPostDtoToItem(ItemPostDto itemPostDto) {
        if (itemPostDto == null) {
            return null;
        }
        return Item.builder()
                .category(itemPostDto.getCategory())
                .category(itemPostDto.getCategory())
                .itemName(itemPostDto.getItemName())
                .itemCount(itemPostDto.getItemCount())
                .description(itemPostDto.getDescription())
                .itemPrice(transPrice(itemPostDto.getItemPrice()))
                .deliveryPrice(transPrice(itemPostDto.getDeliveryPrice()))
                .build();
    }

    public Item itemPatchDtoToItem(ItemPatchDto itemPatchDto) {
        if (itemPatchDto == null) {
            return null;
        }
        return Item.builder()
                .category(itemPatchDto.getCategory())
                .category(itemPatchDto.getCategory())
                .itemName(itemPatchDto.getItemName())
                .itemCount(itemPatchDto.getItemCount())
                .description(itemPatchDto.getDescription())
                .itemPrice(transPrice(itemPatchDto.getItemPrice()))
                .deliveryPrice(transPrice(itemPatchDto.getDeliveryPrice()))
                .build();

    }
    // Price Method
    private Price transPrice(Integer price) {
        return Price.builder().value(price).build();
    }

    // itemResponse Mapper
    public ItemResponseDto itemToItemResponseDto(Item item) {
        if (item == null) return null;
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .itemId(item.getItemId())
                .category(item.getCategory())
                .itemName(item.getItemName())
                .itemCount(item.getItemCount())
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
                    .itemName(item.getItemName())
                    .itemCount(item.getItemCount())
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
    public ItemIdResponseDto itemIdResponseDto(Item item) {
        return new ItemIdResponseDto(item.getItemId());
    }

    // itemOption Mapper
    public ItemOption optionPostDtoToOption(OptionPostDto optionPostDto) {
        return ItemOption.builder()
                .optionDetail(optionPostDto.getOptionDetail())
                .build();
    }

    public ItemOption optionPatchToOption(OptionPatchDto optionPatchDto) {
        return ItemOption.builder()
                .optionId(optionPatchDto.getOptionId())
                .optionDetail(optionPatchDto.getOptionDetail())
                .build();
    }

    public ItemOption optionResponseToOption(ItemOption itemOption) {
        return ItemOption.builder()
                .optionId(itemOption.getOptionId())
                .optionDetail(itemOption.getOptionDetail())
                .build();
    }

        // itemSpec Mapper
    public ItemSpec specPostDtoToSpec(SpecPostDto specPostDto) {
        return ItemSpec.builder()
                .content(specPostDto.getContent())
                .build();
    }

    public ItemSpec specPatchDtoToSpec(SpecPatchDto specpatchDto) {
        return ItemSpec.builder()
                .specId(specpatchDto.getSpecId())
                .content(specpatchDto.getContent())
                .build();
    }

    public ItemSpec specToSpecResponse(ItemSpec itemSpec) {
        return ItemSpec.builder()
                .specId(itemSpec.getSpecId())
                .itemName(itemSpec.getItemName())
                .content(itemSpec.getContent())
                .build();
    }

    public OptionResponseDto optionToGetResponse(ItemOption option) {
        return new OptionResponseDto(option);
    }

    public List<OptionResponseDto> optionToGetListResponse(List<ItemOption> optionList) {
        return optionList.stream().map(option -> new OptionResponseDto(option)).collect(Collectors.toList());
    }

}





