package project.main.webstore.domain.item.service;

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
import project.main.webstore.domain.image.utils.ImageUtils;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.repository.ItemRepository;
import project.main.webstore.domain.item.repository.SpecRepository;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.stub.ImageStub;

import java.util.List;

import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {
    @InjectMocks
    ItemService service;
    @Mock
    ItemValidService itemValidService;
    @Mock
    ItemRepository itemRepository;
    @Mock
    SpecRepository specRepository;
    @Mock
    UserValidService userValidService;
    @Mock
    ImageUtils imageUtils;
    ItemStub itemStub = new ItemStub();
    ImageStub imageStub = new ImageStub();

    @Test
    @DisplayName("상품 등록[이미지 없음] : 성공")
    void post_item_no_image_test() throws Exception{
        // given
        Item item = itemStub.createItem(1L);
        Item itemNoId = itemStub.createItemNoId();
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
    void post_item_test() throws Exception{
        // given
        Item item = itemStub.createItem(1L);
        Item itemNoId = itemStub.createItemNoId();
        List<ImageInfoDto> imageInfo = imageStub.createImageInfo(1,true);
        List<Image> imageList = imageStub.createImageList(2);

        given(imageUtils.uploadImageList(anyList())).willReturn(imageList);
        given(itemRepository.save(ArgumentMatchers.any(Item.class))).willReturn(item);
        // when
        Item result = service.postItem(itemNoId,imageInfo);
        // then
        Assertions.assertThat(itemNoId.getItemId()).isNull();
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(result.getItemPrice()).isEqualTo(item.getItemPrice());
        Assertions.assertThat(result.getItemId()).isEqualTo(1L);
        Assertions.assertThat(itemNoId.getItemId()).isNull();
    }


}