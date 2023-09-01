package project.main.webstore.domain.users.controller;

import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.users.dto.UserIdResponseDto;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.mapper.UserMapper;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.helper.TestUtils;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.utils.FileUploader;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.main.webstore.utils.ApiDocument.getRequestPreProcessor;
import static project.main.webstore.utils.ApiDocument.getResponsePreProcessor;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    TestRestTemplate template = new TestRestTemplate();
    @Autowired
    JwtTokenizer jwtTokenizer;
    @Autowired
    TestUtils testUtils;
    @Autowired
    MockMvc mvc;
    @Autowired
    Gson gson;
    @MockBean
    FileUploader fileUploader;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private ImageMapper imageMapper;
    @LocalServerPort
    private int port;
    private UserStub userStub = new UserStub();

    @Test
    @DisplayName("유저 회원가입 TEST")
    void postUserTest() throws Exception {
        // given
        UserPostRequestDto requestDto = new UserPostRequestDto("admin221@gmail.com", "asdffcx1111", "김송모자리", "010-8013-1313", "김송모");
        String content = gson.toJson(requestDto);

        MockMultipartFile multipartFile = new MockMultipartFile("image", "image.ong", MediaType.IMAGE_PNG_VALUE, "TEST Mock".getBytes());
        MockMultipartFile postUser = new MockMultipartFile("post", "post", "application/json", content.getBytes(StandardCharsets.UTF_8));

        User user = userMapper.toEntity(requestDto);
        user.setId(1L);
        ImageInfoDto infoDto = imageMapper.toLocalDto(multipartFile, "user");

        User result = userService.postUser(user, infoDto);
        UserIdResponseDto responseDto = userMapper.toDto(result);

        when(fileUploader.uploadImage(any(ImageInfoDto.class))).thenReturn(new Image("image", null, null, null, null, 0, true, null));
        when(userService.postUser(any(User.class), any(ImageInfoDto.class))).thenReturn(result);
        when(userMapper.toDto(any(User.class))).thenReturn(responseDto);


        // when
        ResultActions actions = mvc.perform(multipart("/api/users")
            .file("image", "TEST Mock".getBytes())
            .file(multipartFile)
            .file(postUser)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .content(content)
        );

        // then
        actions
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", is("/api/users/{userId}")))
//            .andExpect(jsonPath("$.data.userId").value(1L)
//                .andExpect(jsonPath("$.data.email").value(requestDto.getEmail()))
//                .andExpect(jsonPath("$.data.nickname").value(requestDto.getNickname()))
//                .andExpect(jsonPath("$.data.phone").value(requestDto.getPhone()))
//                .andExpect(jsonPath("$.data.userName").value(requestDto.getUserName()))
            .andDo(document(
                "post-user",
                getRequestPreProcessor(),
                getResponsePreProcessor(),
                requestParts(
                    partWithName("post").description("사용자 등록을 위한 정보 입력, 데이터 전달 시 application/json 형식으로 전달"),
                    partWithName("image").description("이미지 파일 업로드")
                ),
                requestFields(
                    List.of(
                        fieldWithPath("email").type(JsonFieldType.STRING).description("유저 email"),
                        fieldWithPath("password").type(JsonFieldType.STRING).description("유저 password"),
                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("유저 닉네임"),
                        fieldWithPath("phone").type(JsonFieldType.STRING).description("유저 전화번호"),
                        fieldWithPath("userName").type(JsonFieldType.STRING).description("유저 이름")
                    )),
                responseFields(
                    List.of(
                        fieldWithPath("response").type(JsonFieldType.OBJECT).description("결과 데이터"),
                        fieldWithPath("response.id").type(JsonFieldType.NUMBER).description("유저 ID"),
                        fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                        fieldWithPath("Code").type(JsonFieldType.NUMBER).description("HttpCode")
                    )),
                responseHeaders(
                    headerWithName(HttpHeaders.LOCATION).description("Location header. 등록된 리소스의 URI")
                )
            ));
    }
}
