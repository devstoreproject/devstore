package project.main.webstore.domain.item.stub;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import project.main.webstore.domain.image.dto.ImageSortPatchDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.stub.ImageStub;

public class ItemStub extends ImageStub {
    public ItemPostDto createPostDtoWithImage(){
        return new ItemPostDto(Category.COMPUTER,"맥북",10,"이것 맥북의 설명입니다.",1000000,100,3000, createOptionPostList(),createImageList());
    }
    public ItemPostDto createPostDtoNoImage(){
        return new ItemPostDto(Category.COMPUTER,"맥북",10,"이것 맥북의 설명입니다.",1000000,100,3000, createOptionPostList());
    }

    //응답 데이터 용으로 사용
    public Item createItem(Long itemId) {
        Item item = Item.builder()
                .itemId(itemId)
                .itemName("상품 이름" + itemId)
                .itemPrice((int)(3000 * (itemId * 1000)))
                .deliveryPrice((int)(itemId * 1000))
                .discountRate(0)
                .category(Category.CHAIR)
                .build();
        item.addDefaultItem(createDefaultOption(1L));
        item.addOptionList(createOptionListWithId());
        return item;
    }

    //요청 데이터로 사용
    public Item createItemNoId(Long index) {
        Item item = Item.builder()
                .itemName("상품 이름" + index)
                .itemPrice((int)(3000 * (index * 1000)))
                .deliveryPrice((int)(index * 1000))
                .discountRate(0)
                .category(Category.CHAIR)
                .build();
        item.addDefaultItem(createDefaultOption());
        item.addOptionList(createOptionListNoId());
        return item;
    }

    public Page<Item> createPageItem(Long limit){
        List<Item> list = createItemList(limit);
        Pageable pageInfo = super.getPage();
        return new PageImpl<>(list,pageInfo,30);
    }


    public Item createItemByPatchNoImage() {
        Item item = Item.builder()
                .category(Category.CHAIR)
                .itemName("의자")
                .description("이것은 의자 상품입니다")
                .itemPrice(10000)
                .deliveryPrice(3000)
                .discountRate(0)
                .build();
        item.addDefaultItem(new ItemOption(0,100,null));
        return item;
    }

    public ItemPatchDto createPatchNoImage(){
        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10);
    }
    public ItemPatchDto createPatchWithImage() {
        return ItemPatchDto.builder()
                .category(Category.CHAIR)
                .name("의자")
                .description("이것은 의자 상품입니다")
                .itemPrice(10000)
                .discountRate(0)
                .deliveryPrice(3000)
                .defaultCount(100)
                .imageSortAndRepresentativeInfo(List.of(new ImageSortPatchDto(1L,2,false)))
                .deleteImageId(List.of(1L))
                .build();
    }
    public ItemPatchDto createPatchWithImageTwo() {
        return ItemPatchDto.builder()
                .category(Category.CHAIR)
                .name("의자")
                .description("이것은 의자 상품입니다")
                .itemPrice(10000)
                .discountRate(0)
                .deliveryPrice(3000)
                .defaultCount(100)
                .imageSortAndRepresentativeInfo(List.of(new ImageSortPatchDto(1L,2,false),new ImageSortPatchDto(null,1,true),new ImageSortPatchDto(null,2,false)))
                .deleteImageId(List.of(1L))
                .build();
    }
    public ItemPatchDto createPatchChangeDeleteImageId() {
        return ItemPatchDto.builder()
                .category(Category.CHAIR)
                .name("의자")
                .description("이것은 의자 상품입니다")
                .itemPrice(10000)
                .discountRate(0)
                .deliveryPrice(3000)
                .defaultCount(100)
                .deleteImageId(List.of(1L))
                .build();
    }

    //리펙토링 필요
    private List<OptionPostRequestDto> createOptionPostList(){
        return List.of(
                new OptionPostRequestDto("옵션 상세 설정1",100,100000,"옵션 이름1"),
                new OptionPostRequestDto("옵션 상세 설정2",200,200000,"옵션 이름2"),
                new OptionPostRequestDto("옵션 상세 설정3",300,300000,"옵션 이름3"),
                new OptionPostRequestDto("옵션 상세 설정4",400,400000,"옵션 이름4")
        );
    }
    //리펙토링 필요
    private List<ItemOption> createOptionListNoId() {
        List<ItemOption> list = new ArrayList<>();
        for(Long i = 2L ; i <=4L ; i++){
            list.add(createOptionNoId(i));
        }
        return list;
    }
    private List<ItemOption> createOptionListWithId() {
        List<ItemOption> list = new ArrayList<>();
        for(Long i = 2L ; i <=4L ; i++){
            list.add(createOption(i));
        }
        return list;
    }
    private ItemOption createOption(Long optionId) {
        return new ItemOption(optionId,"옵션 설명" + optionId,null,100,10000,false,null,new ArrayList<>(), new ArrayList<>());
    }
    private ItemOption createOptionNoId(Long index) {
        return new ItemOption("옵션 설명" + index,100,10000,"옵션 이름" + index);
    }
    private ItemOption createDefaultOption() {
        return new ItemOption(0,100,null);
    }
    private ItemOption createDefaultOption(Long optionId) {
        return new ItemOption(optionId,"",null,100,null);
    }

    private List<Item> createItemList(Long limit) {
        List<Item> list = new ArrayList<>();
        for(long i = 1L; i < limit;i++){
            list.add(createItem(i));
        }
        return list;
    }
}
