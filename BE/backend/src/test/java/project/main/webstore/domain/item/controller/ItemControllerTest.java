package project.main.webstore.domain.item.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.anyList;
import static org.mockito.BDDMockito.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.stub.ImageStub;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class ItemControllerTest {
    final String DEFAULT_URL = "/api/items";
    @Autowired
    MockMvc mvc;
    @Autowired
    Gson gson;
    ItemStub itemStub = new ItemStub();
    ImageStub imageStub = new ImageStub();
    @MockBean
    ItemService itemService;

    @Test
    @DisplayName("상품 등록 [이미지 삽입]")
    @WithMockCustomUser
    void create_item_with_image_test() throws Exception {
        //stub 생성
        ItemPostDto post = itemStub.createPostDtoWithImage();
        String content = gson.toJson(post);
        Item result = itemStub.createItem(1L);
        MockMultipartFile multipartFile = new MockMultipartFile("imageList", "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("imageList", "originalFilename2.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile postItem = new MockMultipartFile("post", "post", "application/json", content.getBytes(StandardCharsets.UTF_8));


        given(itemService.postItem(any(Item.class), anyList())).willReturn(result);

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.multipart(DEFAULT_URL).file(multipartFile2).file(multipartFile).file(postItem).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }

    @Test
    @DisplayName("상품 등록")
    @WithMockCustomUser
    void create_item_test() throws Exception {
        ItemPostDto post = itemStub.createPostDtoNoImage();
        String content = gson.toJson(post);
        Item result = itemStub.createItem(1L);
        MockMultipartFile postItem = new MockMultipartFile("post", "post", "application/json", content.getBytes(StandardCharsets.UTF_8));


        given(itemService.postItem(any(Item.class))).willReturn(result);

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.multipart(DEFAULT_URL).file(postItem).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }

    @Test
    @DisplayName("상품 수정")
    @WithMockCustomUser
    void patch_item_no_image_test() throws Exception {
        ItemPatchDto patch = itemStub.createPatchNoImage();
        String content = gson.toJson(patch);
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json", content.getBytes(StandardCharsets.UTF_8));
        Item item = itemStub.createItem(1L);
        given(itemService.patchItem(any(), any(), any(Item.class), any())).willReturn(item);

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1l).file(patchItem).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }

    @Test
    @DisplayName("상품 수정 :[사진 삭제만]")
    @WithMockCustomUser
    void patch_item_delete_image_test() throws Exception {
        ItemPatchDto patch = itemStub.createPatchChangeDeleteImageId();
        String content = gson.toJson(patch);
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json", content.getBytes(StandardCharsets.UTF_8));
        Item item = itemStub.createItem(1L);
        given(itemService.patchItem(any(), any(), any(Item.class), any())).willReturn(item);

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1l).file(patchItem).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }

    @Test
    @DisplayName("상품 수정 :변경")
    @WithMockCustomUser
    void patch_item_change_image_test() throws Exception {
        ItemPatchDto patch = itemStub.createPatchWithImage();
        String content = gson.toJson(patch);
        MockMultipartFile multipartFile = new MockMultipartFile("imageList", "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json", content.getBytes(StandardCharsets.UTF_8));
        Item item = itemStub.createItem(1L);
        given(itemService.patchItem(anyList(), anyList(), any(Item.class), any())).willReturn(item);

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1l).file(multipartFile).file(patchItem).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }

    @Test
    @WithMockCustomUser
    void deleteItem() throws Exception {
        willDoNothing().given(itemService).deleteItem(anyLong());

        ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URL + "/{item-id}", 1L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("상품 단건 조회 : 로그인 한 회원")
    @WithMockCustomUser(userRole = UserRole.CLIENT)
    void getItem() throws Exception {
        Item result = itemStub.createItem(1L);
        given(itemService.getItem(anyLong(),anyLong())).willReturn(result);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/{item-Id}", 2L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(result.getItemId()));
    }

    @Test
    void searchItem() {
    }

    @Test
    void getItemByCategory() {
    }

    @Test
    void getItemByHighPrice() {
    }

    @Test
    void pickItem() {
    }
}