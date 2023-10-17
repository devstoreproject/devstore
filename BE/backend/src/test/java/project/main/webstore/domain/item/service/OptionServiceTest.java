package project.main.webstore.domain.item.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
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


}