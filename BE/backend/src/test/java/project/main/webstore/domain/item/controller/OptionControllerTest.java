package project.main.webstore.domain.item.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.google.gson.Gson;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.item.dto.OptionPatchDto;
import project.main.webstore.domain.item.dto.OptionPostRequestDto;
import project.main.webstore.domain.item.entity.ItemOption;
import project.main.webstore.domain.item.service.OptionService;
import project.main.webstore.domain.item.stub.ItemStub;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@Transactional
class OptionControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ItemStub itemStub;
    @Autowired
    Gson gson;
    @MockBean
    OptionService optionService;

    String DEFAULT_URL = "/api/items";

    @Test
    @DisplayName("상품 옵션 등록 테스트")
    @WithMockCustomUser
    void post_option_test() throws Exception {
        // given
        OptionPostRequestDto post = itemStub.createPostItemOptionDto();
        String content = gson.toJson(post);
        ItemOption afterServiceMock = itemStub.createItemOption(1L);

        BDDMockito.given(optionService.writeOption(any(ItemOption.class), anyLong()))
                .willReturn(afterServiceMock);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL + "/{itemId}/options", 1L).contentType(
                        MediaType.APPLICATION_JSON).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.optionId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("상품 옵션 수정 테스트")
    @WithMockCustomUser
    void patch_option_test() throws Exception {
        // given
        OptionPatchDto patch = itemStub.creatPatchOptionDto();
        String content = gson.toJson(patch);
        ItemOption afterServiceMock = itemStub.createItemOption(1L);

        BDDMockito.given(optionService.editOption(any(ItemOption.class)))
                .willReturn(afterServiceMock);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_URL + "/options/{optionId}", 1L).contentType(
                        MediaType.APPLICATION_JSON).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.optionId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("상품 옵션 삭제 테스트")
    @WithMockCustomUser
    void delete_option_test() throws Exception {
        // given
        long optionId = 1L;
        BDDMockito.willDoNothing().given(optionService).deleteOption(optionId);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.delete(DEFAULT_URL + "/options/{optionId}", optionId));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("상품 옵션 단건 조회 테스트")
    void get_option_test() throws Exception {
        // given
        Long itemId = 1L;
        Long optionId = 1L;
        ItemOption afterServiceMock = itemStub.createItemOption(1L);

        BDDMockito.given(optionService.getOption(anyLong())).willReturn(afterServiceMock);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/{itemId}/options/{optionId}", itemId,
                        optionId));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.optionId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.optionDetail").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.additionalPrice").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemCount").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.optionName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString());
    }

    @Test
    @DisplayName("상품별 옵션 전체 조회 테스트")
    void get_option_list_test() throws Exception {
        // given
        Long itemId = 1L;
        List<ItemOption> afterServiceMockList = itemStub.createItemOptionList(10L);
        BDDMockito.given(optionService.getOptions(anyLong())).willReturn(afterServiceMockList);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/{itemId}/options", itemId));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].itemId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].optionId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].optionDetail").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].additionalPrice").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].itemCount").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].optionName").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString());
    }
}