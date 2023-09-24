package project.main.webstore.domain.item.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.entity.ItemImage;
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.exception.ItemExceptionCode;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.exception.BusinessLogicException;
import project.main.webstore.exception.CommonExceptionCode;

@ExtendWith({MockitoExtension.class})
class ItemServiceTest {
    @InjectMocks
    ItemService service;
    @Mock
    ItemValidService itemValidService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    UserValidService userValidService;
    @Mock
    ImageUtils imageUtils;
    ItemStub itemStub = new ItemStub();

    @Test
    @DisplayName("상품 등록[이미지 없음] : 성공")
    void post_item_no_image_test() throws Exception {
        // given
        Item item = itemStub.createItem(1L);
        Item itemNoId = itemStub.createItemNoId(1L);
        given(itemRepository.save(ArgumentMatchers.any(Item.class))).willReturn(item);
        // when
        Item result = service.postItem(itemNoId);
        // then
        Assertions.assertThat(itemNoId.getItemId()).isNull();
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(result.getItemPrice()).isEqualTo(item.getItemPrice());
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(itemNoId.getItemId()).isNull();
    }

    @Test
    @DisplayName("상품 등록[이미지 존재] : 성공")
    void post_item_test() throws Exception {
        // given
        Item item = itemStub.createItem(1L);
        Item itemNoId = itemStub.createItemNoId(1L);
        List<ImageInfoDto> imageInfo = itemStub.createImageInfo(1, true);
        List<Image> imageList = itemStub.createImageList(2);

        given(imageUtils.uploadImageList(anyList())).willReturn(imageList);
        given(itemRepository.save(ArgumentMatchers.any(Item.class))).willReturn(item);
        // when
        Item result = service.postItem(itemNoId, imageInfo);
        // then
        Assertions.assertThat(itemNoId.getItemId()).isNull();
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(result.getItemPrice()).isEqualTo(item.getItemPrice());
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(itemNoId.getItemId()).isNull();
    }
    //이미지 등록 실패하는 경우는 이미지가 조회가 되지 않는 경우 -> validService에서 검증 -> 따로 검증을 진행하지 않아도 된다.
    @Test
    @DisplayName("상품 수정 : 성공")
    void patch_item_test() throws Exception {
        // given
        Item itemByPatchNoImage = itemStub.createItemByPatchNoImage();
        itemByPatchNoImage.setItemId(1L);
        Item item = itemStub.createItem(1L);
        given(itemValidService.validItem(anyLong())).willReturn(item);
        // when
        Item result = service.patchItem(null, null, itemByPatchNoImage,null);
        // then
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(result.getDeliveryPrice()).isEqualTo(itemByPatchNoImage.getDeliveryPrice());
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(item);

    }

    @Test
    @DisplayName("상품 수정[이미지 및 상품] : 성공")
    void patch_item_delete_image_test() throws Exception {
        // given
        List<ImageInfoDto> imageInfo = itemStub.createImageInfo(1, true);
        List<Image> imageList = itemStub.createImageList(2);
        Item itemByPatchNoImage = itemStub.createItemByPatchNoImage();
        itemByPatchNoImage.setItemId(1L);
        Item item = itemStub.createItem(1L);
        given(itemValidService.validItem(anyLong())).willReturn(item);
        given(imageUtils.patchImage(anyList(), anyList(), anyList())).willReturn(imageList);

        // when
        Item result = service.patchItem(imageInfo, List.of(1L), itemByPatchNoImage ,null);
        // then
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("상품 수정[이미지 및 상품] : 성공")
    void patch_item_no_item_image_test() throws Exception {
        // given
        Item itemEmpty = new Item(1L);

        Item item = itemStub.createItem(1L);
        given(itemValidService.validItem(anyLong())).willReturn(item);
        // when
        Item result = service.patchItem(null, null, itemEmpty,null);
        // then
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(item);
    }
    @Test
    @DisplayName("상품 수정 : 실패")
    void patch_item_exception_test() throws Exception {
        // given
        Item itemEmpty = new Item(1L);

        Item item = itemStub.createItem(1L);
        given(itemValidService.validItem(anyLong())).willThrow(new BusinessLogicException(ItemExceptionCode.ITEM_NOT_FOUND));
        // when then
        Assertions.assertThatThrownBy(() ->   service.patchItem(null, null, itemEmpty,null)).isInstanceOf(BusinessLogicException.class)
                .hasMessage("아이템이 존재하지 않습니다.");

    }
    @Test
    @DisplayName("상품 수정[이미지 삽입] : 실패")
    void patch_item_image_exception_test() throws Exception {
        List<ImageInfoDto> imageInfo = itemStub.createImageInfo(1, true);
        List<Image> imageList = itemStub.createImageList(2);
        Item itemByPatchNoImage = itemStub.createItemByPatchNoImage();
        itemByPatchNoImage.setItemId(1L);
        Item item = itemStub.createItem(1L);
        given(itemValidService.validItem(anyLong())).willReturn(item);
        given(imageUtils.patchImage(anyList(), anyList(), anyList())).willThrow(new BusinessLogicException(CommonExceptionCode.IMAGE_ORDER_ALWAYS_UNIQUE));

        // when then
        Assertions.assertThatThrownBy(() ->   service.patchItem(imageInfo, List.of(1L), itemByPatchNoImage,null)).isInstanceOf(BusinessLogicException.class)
                .hasMessage("이미지 순서는 중복될 수 없습니다.");
    }

    @Test
    @DisplayName("상품 삭제 : 이미지 없음")
    void delete_item_no_image_test() throws Exception{
        // given
        Long itemId = 1L;
        given(itemValidService.validItem(anyLong())).willReturn(itemStub.createItemByPatchNoImage());
        willDoNothing().given(itemRepository).delete(any(Item.class));
        // when
        service.deleteItem(itemId);
        // then
        verify(itemValidService, times(1)).validItem(itemId);
        verify(itemRepository, times(1)).delete(any(Item.class));

    }
    @Test
    @DisplayName("상품 삭제 : 이미지 없음")
    void delete_item_with_image_test() throws Exception{
        // given
        Long itemId = 1L;
        Item item = itemStub.createItem(1L);
        List<Image> imageList = itemStub.createImageList(2);
        List<ItemImage> image = imageList.stream().map(imageE -> new ItemImage(imageE, item)).collect(Collectors.toList());
        item.setItemImageList(image);

        given(itemValidService.validItem(anyLong())).willReturn(item);
        willDoNothing().given(itemRepository).delete(any(Item.class));
        willDoNothing().given(imageUtils).deleteImage(anyList());
        // when
        service.deleteItem(itemId);
        // then
        verify(itemValidService, times(1)).validItem(itemId);
        verify(itemRepository, times(1)).delete(any(Item.class));
    }
}