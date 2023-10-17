package project.main.webstore.domain.item.controller;

import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.MultiValueMap;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.item.dto.ItemPatchDto;
import project.main.webstore.domain.item.dto.ItemPostDto;
import project.main.webstore.domain.item.entity.Item;
import project.main.webstore.domain.item.enums.Category;
import project.main.webstore.domain.item.service.ItemService;
import project.main.webstore.domain.item.stub.ItemStub;
import project.main.webstore.domain.users.enums.UserRole;

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
        MockMultipartFile multipartFile = new MockMultipartFile("imageList",
                "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("imageList",
                "originalFilename2.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile postItem = new MockMultipartFile("post", "post", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        given(itemService.postItem(any(Item.class), anyList())).willReturn(result);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(DEFAULT_URL).file(multipartFile2)
                        .file(multipartFile).file(postItem).accept(MediaType.APPLICATION_JSON));

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
        MockMultipartFile postItem = new MockMultipartFile("post", "post", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        given(itemService.postItem(any(Item.class))).willReturn(result);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(DEFAULT_URL).file(postItem)
                        .accept(MediaType.APPLICATION_JSON));

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
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json",
                content.getBytes(StandardCharsets.UTF_8));
        Item item = itemStub.createItem(1L);
        given(itemService.patchItem(any(), any(), any(Item.class), any())).willReturn(item);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1l)
                        .file(patchItem).accept(MediaType.APPLICATION_JSON));

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
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json",
                content.getBytes(StandardCharsets.UTF_8));
        Item item = itemStub.createItem(1L);
        given(itemService.patchItem(any(), any(), any(Item.class), any())).willReturn(item);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1l)
                        .file(patchItem).accept(MediaType.APPLICATION_JSON));

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
        MockMultipartFile multipartFile = new MockMultipartFile("imageList",
                "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile patchItem = new MockMultipartFile("patch", "patch", "application/json",
                content.getBytes(StandardCharsets.UTF_8));
        Item item = itemStub.createItem(1L);
        given(itemService.patchItem(anyList(), anyList(), any(Item.class), any())).willReturn(item);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{itemId}", 1l)
                        .file(multipartFile).file(patchItem).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/items/1"));
    }

    @Test
    @WithMockCustomUser
    void delete_item_test() throws Exception {
        willDoNothing().given(itemService).deleteItem(anyLong());

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.delete(DEFAULT_URL + "/{item-id}", 1L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("상품 단건 조회 : 로그인 한 회원")
    @WithMockCustomUser(userRole = UserRole.CLIENT)
    void get_item_test() throws Exception {
        Item result = itemStub.createItem(1L);
        given(itemService.getItem(anyLong(), anyLong())).willReturn(result);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/{item-Id}", 2L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data.itemId").value(result.getItemId()));
    }

    @Test
    @DisplayName("상품 단건 조회 : 비회원 회원")
    void get_item_not_join_user_test() throws Exception {
        Item result = itemStub.createItem(1L);
        given(itemService.getItem(anyLong(), anyLong())).willReturn(result);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/{item-Id}", 2L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.data.itemId").value(result.getItemId()));
    }

    @Test
    @DisplayName("이름을 통한 상품 검색 ")
    @WithMockCustomUser(role = "CLIENT", userRole = UserRole.CLIENT)
    void search_item_test() throws Exception {
        MultiValueMap pageParam = itemStub.getPageParam();
        pageParam.add("itemName", "의자");
        given(itemService.searchItem(anyString(), any(Pageable.class), anyLong())).willReturn(
                itemStub.createPageItem(3L));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/search/itemName").params(pageParam));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].itemId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.size").value(30));

    }

    @Test
    @DisplayName("카테고리 별 상품 조회")
    void search_item_by_category_test() throws Exception {
        MultiValueMap pageParam = itemStub.getPageParam();
        pageParam.add("category", "CHAIR");
        given(itemService.findItemByCategory(any(Category.class), any(Pageable.class),
                anyLong())).willReturn(itemStub.createPageItem(3L));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL + "/search/category").params(pageParam));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].itemId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.size").value(30));

    }

    @Test
    @DisplayName("찜한 상품 조회")
    @WithMockCustomUser
    void get_pick_item_test() throws Exception {

        given(itemService.getPickedItem(anyLong())).willReturn(itemStub.createItemWhoPickedList(4L));
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/favorite"));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].like").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].like").value(true));
    }

    @Test
    @DisplayName("찜하기 : 찜하기")
    @WithMockCustomUser(role = "CLIENT", userId = 1L, userRole = UserRole.CLIENT)
    void post_pick_item_test() throws Exception {

        given(itemService.pickItem(anyLong(), anyLong())).willReturn(
                itemStub.createPickedItemDto(1L, 1L, true));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL + "/{itemId}/favorite", 1L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.picked").value(true));
    }

    @Test
    @DisplayName("찜하기 : 찜취소하기")
    @WithMockCustomUser(role = "CLIENT", userId = 1L, userRole = UserRole.CLIENT)
    void post_pick_item_cancel_test() throws Exception {

        given(itemService.pickItem(anyLong(), anyLong())).willReturn(
                itemStub.createPickedItemDto(1L, 1L, false));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.post(DEFAULT_URL + "/{itemId}/favorite", 1L));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.picked").value(false));

    }

    @Test
    @DisplayName("전체 조회")
    void get_all_page() throws Exception {
        MultiValueMap pageParam = itemStub.getPageParam();
        given(itemService.findItemPage(any(Pageable.class), anyLong())).willReturn(
                itemStub.createPageItem(5L));

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.get(DEFAULT_URL).params(pageParam));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[0].itemId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content[1].itemId").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("작업 완료"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("C200"))

        ;
    }
}
