package project.main.webstore.domain.item.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.DefaultMapper;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.item.dto.ItemIdResponseDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.dto.OptionPatchDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.dto.OptionResponseDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.dto.CustomPage;

@Component
public class ItemMapper extends ImageMapper implements DefaultMapper {

    public Item toEntityNew(ItemPostDto post) {
        Item result = Item.builder()
                .itemName(post.getName())
                .description(post.getDescription())
                .discountRate(post.getDiscountRate())
                .itemPrice(post.getItemPrice())
                .deliveryPrice(post.getDeliveryPrice())
                .category(post.getCategory())
                .build();
        result.addOptionList(postToOptionList(post.getOptionList()));
        result.addDefaultItem(toDefaultItemOption(post.getDefaultCount()));
        return result;
    }

    public Item toEntity(ItemPatchDto patch) {
        Item result = Item.builder()
                .itemId(patch.getItemId())
                .category(patch.getCategory())
                .itemName(patch.getName())
                .discountRate(patch.getDiscountRate())
                .description(patch.getDescription())
                .itemPrice(patch.getItemPrice())
                .deliveryPrice(patch.getDeliveryPrice())
                .build();

        result.addDefaultItem(toDefaultItemOption(patch.getDefaultCount()));
        result.addOptionList(patchToOptionList(patch.getUpdateOptionList()));
        return result;
    }

    private List<ItemOption> patchToOptionList(List<OptionPatchDto> optionDtoList) {
        return optionDtoList.stream().map(dto -> new ItemOption(dto)).collect(Collectors.toList());
    }

    private List<ItemOption> postToOptionList(List<OptionPostRequestDto> optionDtoList) {
        return optionDtoList.stream()
                .map(option -> new ItemOption(option.getOptionDetail(), option.getAdditionalPrice(),
                        option.getItemCount(), option.getOptionName(), null))
                .collect(Collectors.toList());
    }

    private ItemOption toDefaultItemOption(Integer optionCount) {
        return new ItemOption(0, optionCount, null);
    }

    public ItemResponseDto toGetResponseDtoNew(Item item) {
        return ItemResponseDto.builder()
                .itemId(item.getItemId())
                .category(item.getCategory())
                .name(item.getItemName())
                .description(item.getDescription())
                .itemPrice(item.getItemPrice())
                .deliveryPrice(item.getDeliveryPrice())
                .defaultCount(item.getDefaultItem().getItemCount())
                .viewCount(item.getViewCount())
                .optionList(toOptionResponseDtoList(item))
                .totalCount(item.getTotalCount())
                .imageList(super.toImageResponseDtoList(item.getItemImageList()))
                .like(item.isLike())
                .salesQuantity(item.getSalesQuantity())
                .build();
    }

    private List<OptionResponseDto> toOptionResponseDtoList(Item item){
        return item.getOptionListWithOutDefault().stream().map(OptionResponseDto::new).collect(Collectors.toList());
    }

    public ItemIdResponseDto toIdResponse(Item item) {
        return new ItemIdResponseDto(item.getItemId());
    }

    public CustomPage<ItemResponseDto> toGetPageResponse(Page<Item> items) {
        Page<ItemResponseDto> map = items.map(ItemResponseDto::new);
        return transCustomPage(map);
    }

    public List<ItemResponseDto> toGetResponseListDto(List<Item> result) {
        return result.stream().map(ItemResponseDto::new).collect(Collectors.toList());
    }

    //삭제 예정 메서드
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

        return new Item(itemPatchDto, itemId);
    }
    public ItemResponseDto toGetResponseDto(Item item) {
        return new ItemResponseDto(item);
    }
}




