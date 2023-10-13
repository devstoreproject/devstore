package project.main.webstore.domain.notice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
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
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.notice.dto.NoticePatchRequestDto;
import project.main.webstore.domain.notice.dto.NoticePostRequestDto;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.domain.notice.service.NoticeGetService;
import project.main.webstore.domain.notice.service.NoticeService;
import project.main.webstore.domain.notice.stub.NoticeStub;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class NoticeControllerTest {

    private static final String DEFAULT_URL = "/api/notices";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private Gson gson;
    @MockBean
    private NoticeService service;
    @MockBean
    private NoticeGetService getService;
    @Autowired
    private NoticeStub noticeStub;

    @Test
    @DisplayName("공지사항 등록 테스트")
    @WithMockCustomUser
    void post_notice_test() throws Exception {
        NoticePostRequestDto post = new NoticePostRequestDto(1L, "title", "content",
                NoticeCategory.EVENT);
        String content = gson.toJson(post);
        Notice resultNotice = new Notice(1L, "title", "content", NoticeCategory.EVENT);

        MockMultipartFile multipartFile = new MockMultipartFile("imageList",
                "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile postNotice = new MockMultipartFile("post", "post", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        given(service.postNotice(any(Notice.class), anyLong())).willReturn(resultNotice);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(DEFAULT_URL).file(multipartFile).file(postNotice)
                        .accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/notice/1"));
    }

    @Test
    @DisplayName("공지사항 등록 테스트")
    @WithMockCustomUser
    void post_notice_no_image_test() throws Exception {
        NoticePostRequestDto post = new NoticePostRequestDto(1L, "title", "content",
                NoticeCategory.EVENT);
        String content = gson.toJson(post);
        Notice resultNotice = new Notice(1L, "title", "content", NoticeCategory.EVENT);

        MockMultipartFile postNotice = new MockMultipartFile("post", "post", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        given(service.postNotice(any(Notice.class), anyLong())).willReturn(resultNotice);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(DEFAULT_URL).file(postNotice)
                        .accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/notice/1"));
    }

    @Test
    @DisplayName("공지사항 수정 테스트 : 파일만")
    @WithMockCustomUser
    void patch_notice_test() throws Exception {
        NoticePatchRequestDto patch = new NoticePatchRequestDto(1L, "title", "content",
                NoticeCategory.EVENT);
        String content = gson.toJson(patch);
        Notice resultNotice = new Notice(1L, "title", "content", NoticeCategory.EVENT);

        MockMultipartFile patchEntity = new MockMultipartFile("patch", "patch", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        given(service.patchNotice(isNull(), any(Notice.class))).willReturn(resultNotice);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{noticeId}", 1L)
                        .file(patchEntity)
                        .accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/notice/1"));
    }

    @Test
    @DisplayName("공지사항 수정 테스트 : 이미지만")
    @WithMockCustomUser
    void patch_notice_only_image_test() throws Exception {
        Notice resultNotice = new Notice(1L, "title", "content", NoticeCategory.EVENT);

        MockMultipartFile multipartFile = new MockMultipartFile("imageList",
                "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());

        given(service.patchNotice(any(ImageInfoDto.class), any(Notice.class))).willReturn(
                resultNotice);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{noticeId}", 1L)
                        .file(multipartFile)
                        .accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/notice/1"));
    }

    @Test
    @DisplayName("공지사항 수정 테스트 : 이미지와 데이터")
    @WithMockCustomUser
    void patch_notice_with_image_test() throws Exception {
        NoticePatchRequestDto patch = new NoticePatchRequestDto(1L, "title", "content",
                NoticeCategory.EVENT);
        String content = gson.toJson(patch);

        MockMultipartFile multipartFile = new MockMultipartFile("imageList",
                "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile patchEntity = new MockMultipartFile("patch", "patch", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        Notice resultNotice = new Notice(1L, "title", "content", NoticeCategory.EVENT);

        given(service.patchNotice(any(ImageInfoDto.class), any(Notice.class))).willReturn(
                resultNotice);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(HttpMethod.PATCH, DEFAULT_URL + "/{noticeId}", 1L)
                        .file(multipartFile).file(patchEntity)
                        .accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/notice/1"));
    }

    @Test
    @DisplayName("공지 사항 삭제 테스트")
    @WithMockCustomUser
    void delete_notice_test() throws Exception{
        // given
        willDoNothing().given(service).deleteNotice(anyLong());
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.delete(DEFAULT_URL));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("공지 전체 조회")
    void get_notice_all_test() throws Exception{
        // given
        MultiValueMap<String,String> param = noticeStub.getPageParam();
        param.add("category",NoticeCategory.EVENT.name());

        given(getService.getSimpleNotice(any(Pageable.class),anyString())).willReturn(noticeStub.createEntityPage(30L,NoticeCategory.EVENT));
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL).params(param));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.offset").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.size").value(30))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.pageable.totalElements").value(30));
    }

    @Test
    @DisplayName("공지 단건 조회")
    void get_notice_test() throws Exception{
        // given
        Notice result = noticeStub.createEntity(1L);
        given(getService.getNotice(anyLong())).willReturn(result);
        // when
        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get(DEFAULT_URL + "/{noticeId}",1L));
        // then
        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(result.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value(result.getTitle()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value(result.getContent()));
    }
}