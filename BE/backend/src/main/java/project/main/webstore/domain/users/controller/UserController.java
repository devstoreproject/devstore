package project.main.webstore.domain.users.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.users.dto.*;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.mapper.UserMapper;
import project.main.webstore.domain.users.service.UserService;
import project.main.webstore.dto.ResponseDto;
import project.main.webstore.enums.ResponseCode;
import project.main.webstore.utils.CheckLoginUser;
import project.main.webstore.utils.UriCreator;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "사용자 API", description = "사용자 조회 등의 기능 포함")
public class UserController {
    private final String UPLOAD_DIR = "user";
    private final UserService service;
    private final UserMapper userMapper;
    private final ImageMapper imageMapper;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponse(responseCode = "201", description = "회원 가입")
    public ResponseEntity<ResponseDto<UserIdResponseDto>> postUser(
            @RequestPart UserPostRequestDto post,
            @RequestPart(required = false) MultipartFile image) {
        User user = userMapper.toEntity(post);
        ImageInfoDto infoDto = null;
        if (image != null) {
            infoDto = imageMapper.toLocalDto(image, UPLOAD_DIR);
        }
        User result = service.postUser(user, infoDto);
        UserIdResponseDto response = userMapper.toDto(result);
        var responseDto = ResponseDto.<UserIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI location = UriCreator.createUri("/users/{userId}", response.getUserId());

        return ResponseEntity.created(location).body(responseDto);
    }

    @PatchMapping(path = "/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "사용자 정보 수정")
    public ResponseEntity<ResponseDto<UserIdResponseDto>> patchUser(@PathVariable Long userId,
                                                                    @RequestPart UserPatchRequestDto patch,
                                                                    @RequestPart(required = false) MultipartFile image,
                                                                    @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        User request = userMapper.toEntity(patch);
        request.setId(userId);
        ImageInfoDto infoDto = null;
        if (image != null) {
            infoDto = imageMapper.toLocalDto(image, patch.getImageInfo(), UPLOAD_DIR);
        }
        User result = service.patchUser(request, infoDto);

        UserIdResponseDto response = userMapper.toDto(result);
        var responseDto = ResponseDto.<UserIdResponseDto>builder().data(response).customCode(ResponseCode.CREATED).build();
        URI location = UriCreator.createUri("/users/{userId}", response.getUserId());

        return ResponseEntity.ok().header("Location", location.toString()).body(responseDto);
    }

    //관리자 or 당사자만 볼 수 있음
    @GetMapping("/{userId}")
    @ApiResponse(responseCode = "200", description = "사용자 조회")
    public ResponseEntity getUser(@PathVariable Long userId,
                                  @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        User result = service.getUser(userId);
        UserGetResponseDto response = userMapper.toGetDtoResponse(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    @ApiResponse(responseCode = "200", description = "사용자 정보 리스트 조회\n 관리자만 가능한 코드")
    public ResponseEntity getUserPage(@AuthenticationPrincipal Object principal,
                                      Pageable pageable) {
        CheckLoginUser.validAdmin(principal);
        Page<User> result = service.getUserPage(pageable);
        Page<UserGetResponseDto> response = userMapper.toGetDtoResponse(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204", description = "사용자 삭제")
    public ResponseEntity deleteUser(@PathVariable Long userId,
                                     @AuthenticationPrincipal Object principal) {
        CheckLoginUser.validUserSame(principal, userId);
        service.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/valid-nick")
    @ApiResponse(responseCode = "200",description = "닉네임 중복 검사")
    public ResponseEntity<ResponseDto<Boolean>> validNickName(@Parameter(description = "닉네임",example = "김성자의생활")@RequestParam("nickName") String nickName){
        boolean result = service.checkNickName(nickName);
        ResponseDto<Boolean> responseDto = ResponseDto.<Boolean>builder().data(result).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/auth-mail")
    public ResponseEntity<ResponseDto<UserIdResponseDto>> checkMail(@RequestParam("key") String key){
        User result = service.authMail(key);
        UserIdResponseDto response = userMapper.toDto(result);
        var responseDto = ResponseDto.<UserIdResponseDto>builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/password")
    @ApiResponse(responseCode = "200",description = "새로 발급된는 임시 비밀번호가 들어있다.")
    public ResponseEntity<ResponseDto<UserGetPasswordResponseDto>> changePassword(@RequestBody UserGetPasswordRequestDto get){
        User request = userMapper.toEntity(get);
        User result = service.getTmpPassword(request);
        UserGetPasswordResponseDto response = userMapper.toGetPasswordResponse(result);
        var responseDto = ResponseDto.<UserGetPasswordResponseDto>builder().data(response).customCode(ResponseCode.OK).build();
        return ResponseEntity.ok(responseDto);
    }

}
