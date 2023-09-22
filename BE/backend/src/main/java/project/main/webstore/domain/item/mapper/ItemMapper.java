package project.main.webstore.domain.item.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.item.dto.ItemIdResponseDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.dto.OptionPatchDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;

@Component
public class ItemMapper {
    public Item toEntity(ItemPostDto itemPostDto) {
        if (itemPostDto == null) {
            return null;
        }
        return new Item(itemPostDto);
    }
    public Item toEntity(ItemPatchDto patch) {
        Item result = Item.builder()
                .itemId(patch.getItemId())
                .category(patch.getCategory())
                .itemName(patch.getName())
                .discountRate(patch.getDiscountRate())
                .description(patch.getDescription())
                .defaultItem(toDefaultItemOption(patch.getDefaultCount()))
                .itemPrice(patch.getItemPrice())
                .deliveryPrice(patch.getDeliveryPrice())
                .optionList(toOptionList(patch.getUpdateOptionList()))
                .build();
        result.getDefaultItem().setItem(result);
        //TODO : 리펙토링 대상 한번에 올릴 수 있게 해주는 것
        return result;
    }
    private List<ItemOption> toOptionList(List<OptionPatchDto> optionDtoList){
        //TODO : 리펙토링 대상 : dto 말고 인자로 전달하는 것
        return optionDtoList.stream().map(dto -> new ItemOption(dto)).collect(Collectors.toList());
    }
    private ItemOption toDefaultItemOption(Integer optionCount){
        return new ItemOption(0,optionCount,null);
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




