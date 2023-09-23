package project.main.webstore.domain.item.stub;

import java.util.List;
import project.main.webstore.domain.image.dto.ImageSortPostDto;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.stub.ImageStub;

public class ItemStub extends ImageStub {

    public Item createItem(Long itemId){
        return new Item(itemId,"상품 이름","상품 상세 설명",10000,3000,10,Category.CHAIR, createOptionList());
    }

    public ItemPostDto createPostDtoWithImage(){
        return new ItemPostDto(Category.COMPUTER,"맥북",10,"이것 맥북의 설명입니다.",1000000,100,3000, createOptionPostList(),createImageList());
    }
    public ItemPostDto createPostDtoNoImage(){
        return new ItemPostDto(Category.COMPUTER,"맥북",10,"이것 맥북의 설명입니다.",1000000,100,3000, createOptionPostList());
    }

    public Item createItemNoId() {
        return new Item(createPostDtoWithImage());
    }

    public Item createItemByPatchNoImage() {
        return new Item(createPatchNoImage(),1L);
    }

    public Item createItemByPatchWithImage() {
        return new Item(createPatchWithImage(),1L);
    }

    public Item createItemByPatchChangeImage() {
        return new Item(createPatchWithImage(),1L);
    }

    public ItemPatchDto createPatchNoImage(){
        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10);
    }
    public ItemPatchDto createPatchWithImage() {
        //TODO : Mapper 변경 이 후 수정
//        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10,List.of(1L),null,null,List.of(new ImageSortPatchDto(1L,2,false)));
        return null;
    }
    public ItemPatchDto createPatchWithImageTwo() {
//        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10,List.of(1L),null,null,List.of(new ImageSortPatchDto(1L,2,false),new ImageSortPatchDto(null,1,true),new ImageSortPatchDto(null,2,false)));
        return null;
    }
    public ItemPatchDto createPatchChangeDeleteImageId() {
        return new ItemPatchDto(Category.CHAIR,"의자","이것은 의자 상품입니다",100,10000,300,10,List.of(1L));
    }

    private List<OptionPostRequestDto> createOptionPostList(){
        return List.of(
                new OptionPostRequestDto("옵션 상세 설정1",100,100000,"옵션 이름1"),
                new OptionPostRequestDto("옵션 상세 설정2",200,200000,"옵션 이름2"),
                new OptionPostRequestDto("옵션 상세 설정3",300,300000,"옵션 이름3"),
                new OptionPostRequestDto("옵션 상세 설정4",400,400000,"옵션 이름4")
        );
    }

    private List<ItemOption> createOptionList() {
        return List.of(
                new ItemOption("옵션 설명 1",100,10000,"옵션 이름1"),
                new ItemOption("옵션 설명 2",200,20000,"옵션 이름2"),
                new ItemOption("옵션 설명 3",300,30000,"옵션 이름3")
        );
    }
    private List<ImageSortPostDto> createImageList(){
        return List.of(
                new ImageSortPostDto(0,false),
                new ImageSortPostDto(1,true)
        );
    }

}
