package project.main.webstore.domain.item.stub;

import project.main.webstore.domain.image.dto.ImageSortDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemPostSpecDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;

import java.util.List;

public class ItemStub {

    public Item createItem(Long itemId){
        return new Item(itemId);
    }

    public ItemPostDto createPostDtoWithImage(){
        return new ItemPostDto(Category.COMPUTER,"맥북",10,"이것 맥북의 설명입니다.",1000000,100,3000,createOptionList(),createSpecList(),createImageList());
    }
    public ItemPostDto createPostDtoNoImage(){
        return new ItemPostDto(Category.COMPUTER,"맥북",10,"이것 맥북의 설명입니다.",1000000,100,3000,createOptionList(),createSpecList());
    }

    public Item createItemNoId() {
        return new Item(createPostDtoWithImage());
    }

    public ItemPatchDto createPatchNoImage(){
        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10);
    }
    public ItemPatchDto createPatchWithImage() {
        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10,List.of(1L),List.of(new ImageSortDto(null,2,false)));
    }
    public ItemPatchDto createPatchChangeDeleteImageId() {
        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10,List.of(1L));
    }

    private List<OptionPostRequestDto> createOptionList(){
        return List.of(
                new OptionPostRequestDto("옵션 상세 설정1",100,100000,"옵션 이름1"),
                new OptionPostRequestDto("옵션 상세 설정2",200,200000,"옵션 이름2"),
                new OptionPostRequestDto("옵션 상세 설정3",300,300000,"옵션 이름3"),
                new OptionPostRequestDto("옵션 상세 설정4",400,400000,"옵션 이름4")
        );
    }

    private List<ItemPostSpecDto> createSpecList(){
        return List.of(
                new ItemPostSpecDto("이름1","본문1"),
                new ItemPostSpecDto("이름2","본문2"),
                new ItemPostSpecDto("이름3","본문3"),
                new ItemPostSpecDto("이름4","본문4")
        );

    }
    private List<ImageSortDto> createImageList(){
        return List.of(
                new ImageSortDto(0,false),
                new ImageSortDto(1,true)
        );
    }


}
