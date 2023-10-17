package project.main.webstore.domain.item.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

import com.google.gson.Gson;
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
    void post_option_test() throws Exception{
        // given
        OptionPostRequestDto post = itemStub.createPostItemOptionDto();
        String content = gson.toJson(post);
        ItemOption afterServiceMock = itemStub.createItemOption(1L);

        BDDMockito.given(optionService.writeOption(any(ItemOption.class),anyLong())).willReturn(afterServiceMock);
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
    void patch_option_test() throws Exception{
        // given
        OptionPatchDto patch = itemStub.creatPatchOptionDto();
        String content = gson.toJson(patch);
        ItemOption afterServiceMock = itemStub.createItemOption(1L);

        BDDMockito.given(optionService.editOption(any(ItemOption.class))).willReturn(afterServiceMock);
        // when
        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.patch(DEFAULT_URL + "/options/{option-Id}", 1L).contentType(
                        MediaType.APPLICATION_JSON).content(content));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.optionId").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString());
    }


}