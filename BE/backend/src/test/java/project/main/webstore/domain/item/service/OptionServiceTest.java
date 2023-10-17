package project.main.webstore.domain.item.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

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
        Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(afterRepoMockEntity);
    }


}