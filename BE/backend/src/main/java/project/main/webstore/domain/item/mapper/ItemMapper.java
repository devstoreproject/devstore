package project.main.webstore.domain.item.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.ItemIdResponseDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.entity.Item;

@Component
public class ItemMapper {
    public Item toEntity(ItemPostDto itemPostDto) {
        if (itemPostDto == null) {
            return null;
        }
        return new Item(itemPostDto);
    }

    public Item itemPatchDtoToItem(ItemPatchDto itemPatchDto) {
        if (itemPatchDto == null) {
            return new Item();
        }
        return new Item(itemPatchDto);

    }

    // Price Method

    // itemResponse Mapper
    public ItemResponseDto toGetResponseDto(Item item) {
        return new ItemResponseDto(item);
    }


    public ItemIdResponseDto toIdResponse(Item item) {
        return new ItemIdResponseDto(item.getItemId());
    }

    public Page<ItemResponseDto> toGetPageResponse(Page<Item> items) {
        return items.map(ItemResponseDto::new);
    }
}





