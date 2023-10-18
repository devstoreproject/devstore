package project.main.webstore.domain.item.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.ItemIdResponseDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.entity.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemMapper {
    public Item toEntity(ItemPostDto itemPostDto) {
        if (itemPostDto == null) {
            return null;
        }
        return new Item(itemPostDto);
    }

    public Item itemPatchDtoToItem(ItemPatchDto itemPatchDto, Long itemId) {
        if (itemPatchDto == null) {
            return new Item(itemId);
        }

        return new Item(itemPatchDto,itemId);
    }
    public List<Long> checkListEmpty(List<Long> list) {
        if(list == null)
            return new ArrayList<>();
        return list;
    }
    public ItemResponseDto toGetResponseDto(Item item) {
        return new ItemResponseDto(item);
    }


    public ItemIdResponseDto toIdResponse(Item item) {
        return new ItemIdResponseDto(item.getItemId());
    }

    public Page<ItemResponseDto> toGetPageResponse(Page<Item> items) {
        return items.map(ItemResponseDto::new);
    }

    public List<ItemResponseDto> toGetResponseListDto(List<Item> result) {
        return result.stream().map(ItemResponseDto::new).collect(Collectors.toList());
    }
}





