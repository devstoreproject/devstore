package project.main.webstore.domain.item.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.ItemIdResponseDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.valueObject.Price;

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
        return new Item(itemPatchDto);

    }

    // Price Method
    private Price transPrice(Integer price) {
        return Price.builder().value(price).build();
    }

    // itemResponse Mapper
    public ItemResponseDto toGetResponseDto(Item item) {
        return ItemResponseDto.response().item(item).build();
    }


    public ItemIdResponseDto toIdResponse(Item item) {
        return new ItemIdResponseDto(item.getItemId());
    }

    public Page<ItemResponseDto> toGetPageResponse(Page<Item> items) {
        return items.map(ItemResponseDto::new);
    }
}





