package project.main.webstore.domain.users.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import project.main.webstore.annotation.WithMockCustomUser;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.entity.Image;
import project.main.webstore.domain.users.dto.UserGetPasswordRequestDto;
import project.main.webstore.domain.users.dto.UserGetResponseDto;
import project.main.webstore.domain.users.dto.UserPatchRequestDto;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.enums.UserRole;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.domain.users.service.UserValidService;
import project.main.webstore.domain.users.stub.UserStub;
import project.main.webstore.helper.TestUtils;
import project.main.webstore.security.jwt.utils.JwtTokenizer;
import project.main.webstore.stub.ImageStub;
import project.main.webstore.utils.FileUploader;


@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
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
    private UserValidService validUser;
    private UserStub userStub = new UserStub();
    private ImageStub imageStub = new ImageStub();

    @Test
    @DisplayName("유저 회원가입 TEST")
    void postUserTest() throws Exception {
        // given
        UserPostRequestDto requestDto = new UserPostRequestDto("admin002@gmail.com", "admin12345", "이뽀삐", "010-1234-4568", "이삐뽀");
        String content = gson.toJson(requestDto);
        User post = userStub.createUser(null);
        User user = userStub.createUser(2L);
        ImageInfoDto imageInfoDto = imageStub.createImageInfoDto(1, true);

        MockMultipartFile multipartFile = new MockMultipartFile("image", "image.ong", MediaType.IMAGE_PNG_VALUE, "TEST Mock".getBytes());
        MockMultipartFile postUser = new MockMultipartFile("post", "post", "application/json", content.getBytes(StandardCharsets.UTF_8));

        given(fileUploader.uploadImage(any(ImageInfoDto.class))).willReturn(new Image("image", "original.jpg", "classpath", "jpg", null, 0, true, null));
        given(userService.postUser(any(User.class), any(ImageInfoDto.class))).willReturn(user);

        User result = userService.postUser(post, imageInfoDto);

        // when
        ResultActions actions = mvc.perform(multipart("/api/users")
            .file(multipartFile)
            .file(postUser)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .content(content)
        );

        // then
        actions
            .andDo(log())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.userId").value(2L));
    }

    @Test
    @DisplayName("유저 정보 수정 TEST")
    @WithMockCustomUser(userId = 1L, userRole = UserRole.CLIENT)
    void patchUserTest() throws Exception {
        // given
        User post= userStub.createUser(1L);
        Long userId = 1L;

        UserPatchRequestDto requestDto = new UserPatchRequestDto("admin54321", "뽀삐킴", "010-1234-4321");
        String content = gson.toJson(requestDto);

        MockMultipartFile multipartFile = new MockMultipartFile("image", "image.ong", MediaType.IMAGE_PNG_VALUE, "TEST Mock".getBytes());
        MockMultipartFile patchUser = new MockMultipartFile("patch", "patch", "application/json", content.getBytes(StandardCharsets.UTF_8));
        BDDMockito.given(fileUploader.uploadImage(any(ImageInfoDto.class))).willReturn(new Image("image", null, null, null, null, 0, true, null));

//        given(fileUploader.uploadImage(any(ImageInfoDto.class))).willReturn(new Image("image", "original.jpg", "classpath", "jpg", null, 0, true, null));
        given(userService.patchUser(any(User.class), any(ImageInfoDto.class))).willReturn(post);

        // when
        //multipart 메서드는 post 말고 다른 메서드랑 도사용할 수 있는데 기본 값이 post -> Patch로 변경해줘야함
        ResultActions actions = mvc.perform(multipart(HttpMethod.PATCH,"/api/users/{userId}", userId)
            .file(multipartFile)
            .file(patchUser)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .content(content)
        );

        // then
        actions
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(header().string("Location", "/api/users/1"))
            .andExpect(jsonPath("$.data.userId").value(1L));
    }

    @Test
    @DisplayName("사용자 조회 TEST")
    @WithMockCustomUser(userId = 1L, userRole = UserRole.CLIENT)
    void getUserTest() throws Exception {
        // given
        Long userId = 1L;
        UserGetResponseDto response = new UserGetResponseDto(1L, "admin001@gmail.com", "admin12345", "김뽀삐", null, "010-1234-4567", "김삐뽀");
        User post = userStub.createUser(1L);

        given(userService.getUser(anyLong())).willReturn(post);

        // when
        ResultActions actions = mvc.perform(
            MockMvcRequestBuilders.get("/api/users/{userId}", userId));

        // then
        actions
            .andDo(log())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.userId").value(1L))
        ;
    }

    @Test
    @DisplayName("사용자 전체 조회 TEST")
    @WithMockCustomUser(username = "복자", role = "ADMIN", email = "amdin@gmail.com", userId = 2L, userRole = UserRole.ADMIN)
    void getUsersTest() throws Exception {
        //given
        Page<User> user = userStub.userPage();
        List<UserGetResponseDto> responseDtos = List.of(
            new UserGetResponseDto(1L, "admin001@gmail.com","admin54321","뽀삐킴",null,"010-1234-4321","김삐뽀"),
            new UserGetResponseDto(2L,"admin002@gamil.com","admin12345","이뽀삐",null,"010-1234-4568","이뽀삐")
        );
        Page<UserGetResponseDto> page = userStub.getUserPage();

        MultiValueMap pageParam = testUtils.getPageParam();

        given(userService.getUserPage(any(Pageable.class))).willReturn(user);
        // when
        ResultActions actions = mvc.perform(
            MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));
        // then
        actions
            .andDo(log())
            .andExpect(status().isOk())
        ;
    }

    @Test
    @DisplayName("사용자 정보 Delete")
    @WithMockCustomUser(userId = 1L, userRole = UserRole.CLIENT)
    void deleteUserTest() throws Exception {
        Long userId = 1L;

        doNothing().when(userService).deleteUser(userId);

        ResultActions actions = mvc.perform(
            MockMvcRequestBuilders.delete("/api/users/{userId}", 1L));

        actions
            .andDo(log())
            .andExpect(status().isNoContent());

    }


    @Test
    @DisplayName("사용자 임시 비밀번호 Get")
    void getPassWordTest() throws Exception {
        // given
        UserGetPasswordRequestDto request = new UserGetPasswordRequestDto("admin221@gmail.com", "김송모", "010-8013-1313");
        String content = gson.toJson(request);

        User user = userStub.createUser(1L);

        given(userService.getTmpPassword(any(User.class))).willReturn(content);

        // when
        ResultActions actions = mvc.perform(
            MockMvcRequestBuilders.post("/api/users/password")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        actions
            .andDo(log())
            .andExpect(status().isOk())
        ;
    }


}
