package project.main.webstore.domain.item.mapper;


import java.util.ArrayList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.dto.ItemResponseDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.enums.Category;


class ItemMapperTest {
    ItemMapper mapper = new ItemMapper();

    @Test
    @DisplayName("patch DTO 수정을 위한 테스트 : List들이 빈 값이 들어올 떄 처리 로직")
    void patch_trans_test() throws Exception {
        ItemPatchDto itemPatchDto = new ItemPatchDto(1L, Category.CHAIR, "의자", "설명", 100, 10000,
                3000, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>());

        Item newOne = mapper.toEntity(itemPatchDto);
        Item oldOne = mapper.itemPatchDtoToItem(itemPatchDto, 1L);

        Assertions.assertThat(newOne).as("두 값은 동일해야한다.").usingRecursiveComparison()
                .isEqualTo(oldOne);
    }

    @Test
    @DisplayName("post DTO 수정을 위한 테스트")
    void post_trans_test() throws Exception{
        ItemPostDto post = new ItemPostDto(Category.CHAIR, "의자", 0, "상세 설명", 10000,
                100, 3000, new ArrayList<>(), new ArrayList<>());

        Item oldOne = mapper.toEntity(post);
        Item newOne = mapper.toEntityNew(post);

        Assertions.assertThat(newOne).as("두 값은 동일해야한다.").usingRecursiveComparison()
                .isEqualTo(oldOne);
    }




    @Test
    @DisplayName("Entity -> DTO 변환 테스트")
    void entity_trans_to_dto_test() throws Exception{
        Item entity = new Item(new ArrayList<>(), 1L, "상품", "상품 설명", 30000, 3000, 0, Category.CHAIR,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new ArrayList<>(), new ItemOption(0,1000,null));

        ItemResponseDto newOne = mapper.toGetResponseDtoNew(entity);
        ItemResponseDto oldOne = mapper.toGetResponseDto(entity);

        Assertions.assertThat(newOne).as("두 값은 동일해야한다.").usingRecursiveComparison()
                .isEqualTo(oldOne);
    }
}