package project.main.webstore.domain.notice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.notice.dto.NoticePostRequestDto;
import project.main.webstore.domain.notice.entity.Notice;
import project.main.webstore.domain.notice.enums.NoticeCategory;
import project.main.webstore.domain.notice.service.NoticeGetService;
import project.main.webstore.domain.notice.service.NoticeService;
import project.main.webstore.helper.TestUtils;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
class NoticeControllerTest {

    private static final String DEFAULT_URL ="/api/notices";
    @Autowired
    private MockMvc mvc;
    @Autowired
    private Gson gson;
    @MockBean
    private NoticeService service;
    @MockBean
    private NoticeGetService getService;
    @Autowired
    private TestUtils testUtils;

    @Test
    @DisplayName("공지사항 등록 테스트")
    @WithMockCustomUser
    void post_notice_test() throws Exception{
        NoticePostRequestDto post = new NoticePostRequestDto(1L, "title", "content",
                NoticeCategory.EVENT);
        String content = gson.toJson(post);
        Notice resultNotice = new Notice(1L, "title", "content",NoticeCategory.EVENT);

        MockMultipartFile multipartFile = new MockMultipartFile("imageList",
                "originalFilename1.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile multipartFile2 = new MockMultipartFile("imageList",
                "originalFilename2.jpg", "jpg", "TEST Mock".getBytes());
        MockMultipartFile postNotice = new MockMultipartFile("post", "post", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        given(service.postNotice(any(Notice.class),anyLong())).willReturn(resultNotice);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(DEFAULT_URL).file(multipartFile2)
                        .file(multipartFile).file(postNotice).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/notice/1"));
    }

    @Test
    @DisplayName("공지사항 등록 테스트")
    @WithMockCustomUser
    void post_notice_no_image_test() throws Exception{
        NoticePostRequestDto post = new NoticePostRequestDto(1L, "title", "content",
                NoticeCategory.EVENT);
        String content = gson.toJson(post);
        Notice resultNotice = new Notice(1L, "title", "content",NoticeCategory.EVENT);

        MockMultipartFile postNotice = new MockMultipartFile("post", "post", "application/json",
                content.getBytes(StandardCharsets.UTF_8));

        given(service.postNotice(any(Notice.class),anyLong())).willReturn(resultNotice);

        ResultActions perform = mvc.perform(
                MockMvcRequestBuilders.multipart(DEFAULT_URL).file(postNotice).accept(MediaType.APPLICATION_JSON));

        perform
                .andDo(MockMvcResultHandlers.log())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.noticeId").value(1L))
                .andExpect(MockMvcResultMatchers.header().string("Location", "/api/notice/1"));
    }


}