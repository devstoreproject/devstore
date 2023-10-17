package project.main.webstore.domain.item.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.repository.OptionRepository;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.order.exception.OrderExceptionCode;

@ExtendWith(MockitoExtension.class)
class OptionServiceTest {
    @InjectMocks
    OptionService service;
    @Mock
    OptionRepository optionRepository;
    @Mock
    ItemValidService itemValidService;
    ItemStub itemStub = new ItemStub();

    @Test
    @DisplayName("옵션 등록 테스트 : 성공")
    void post_option_test() throws Exception{
        // given
        ItemOption post = itemStub.createItemOption();
        ItemOption afterRepoMockEntity = itemStub.createItemOption(1L);

        given(itemValidService.validItem(anyLong())).willReturn(itemStub.createItem(1L));
        given(optionRepository.save(any(ItemOption.class))).willReturn(afterRepoMockEntity);
        // when
        ItemOption result = service.writeOption(post, 1L);
        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(afterRepoMockEntity);
    }

    @Test
    @DisplayName("옵션 등록 테스트 : 성공")
    void patch_option_test() throws Exception{
        // given
        ItemOption afterRepoMockEntity = itemStub.createItemOption(1L);
        ItemOption patch = itemStub.createItemOption(1L);
        patch.setOptionName("수정할이름");
        patch.setOptionDetail("수정할디테일");
        given(optionRepository.findById(anyLong())).willReturn(Optional.of(afterRepoMockEntity));
        // when
        ItemOption result = service.editOption(patch);
        // then
        assertThat(result.getOptionName()).isEqualTo(patch.getOptionName());
        assertThat(result.getOptionDetail()).isEqualTo(patch.getOptionDetail());
    }

    @Test
    @DisplayName("옵션 등록 테스트 : 실패[조회 불가]")
    void patch_option_fail_test() throws Exception{
        // given
        ItemOption patch = itemStub.createItemOption(1L);
        patch.setOptionName("수정할이름");
        patch.setOptionDetail("수정할디테일");
        given(optionRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(()-> service.editOption(patch)).hasMessage(OrderExceptionCode.OPTION_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("옵션 삭제 테스트 : 실패[조회 불가]")
    void delete_option_fail_test() throws Exception{
        // given
        given(optionRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(()-> service.deleteOption(1L)).hasMessage(OrderExceptionCode.OPTION_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("옵션 삭제 테스트 : 성공")
    void delete_option_test() throws Exception{
        // given
        given(optionRepository.findById(anyLong())).willReturn(Optional.of(itemStub.createItemOption(1L)));
        willDoNothing().given(optionRepository).delete(any(ItemOption.class));
        // when
        service.deleteOption(1L);
        // then
        verify(optionRepository,times(1)).findById(anyLong());
        verify(optionRepository,times(1)).delete(any(ItemOption.class));
    }

    @Test
    @DisplayName("옵션 단건 조회 테스트 : 성공")
    void get_option_test() throws Exception{
        // given
        ItemOption mockEntity = itemStub.createItemOption(1L);
        given(optionRepository.findById(anyLong())).willReturn(Optional.of(mockEntity));
        // when
        ItemOption result = service.getOption(1L);
        // then
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(mockEntity);
    }

    @Test
    @DisplayName("옵션 단건 조회 테스트 : 실패")
    void get_option_fail_test() throws Exception{
        // given
        given(optionRepository.findById(anyLong())).willReturn(Optional.empty());
        // when
        // then
        assertThatThrownBy(() -> service.getOption(1L)).hasMessage(OrderExceptionCode.OPTION_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("옵션 리스트 별 조회 조회 테스트")
    void get_option_list_by_list_test() throws Exception{
        // given
        List<ItemOption> mockEntityList = itemStub.createItemOptionList(2L);
        given(optionRepository.findInId(anyList())).willReturn(mockEntityList);
        // when
        List<ItemOption> result = service.getOptions(List.of(1L, 2L));
        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.stream().map(ItemOption::getOptionId)).contains(1L,2L);
    }
    @Test
    @DisplayName("옵션 리스트 별 조회 조회 테스트")
    void get_option_list_by_item_id_test() throws Exception{
        // given
        List<ItemOption> mockEntityList = itemStub.createItemOptionList(2L);
        given(optionRepository.findAllByItemId(anyLong())).willReturn(mockEntityList);
        // when
        List<ItemOption> result = service.getOptions(1L);
        // then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result.stream().map(ItemOption::getOptionId)).contains(1L,2L);
    }

}