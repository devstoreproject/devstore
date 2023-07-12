package project.main.webstore.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.main.webstore.domain.image.dto.ImageInfoDto;
import project.main.webstore.domain.image.mapper.ImageMapper;
import project.main.webstore.domain.users.dto.UserGetResponseDto;
import project.main.webstore.domain.users.dto.UserIdResponseDto;
import project.main.webstore.domain.users.dto.UserPatchRequestDto;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
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
public class UserController {
    private final String UPLOAD_DIR = "user";
    private final UserService service;
    private final UserMapper userMapper;
    private final ImageMapper imageMapper;
    @PostMapping
    public ResponseEntity postUser(@RequestPart UserPostRequestDto post,
                                   @RequestPart(required = false) MultipartFile image) {
        User user = userMapper.toEntity(post);
        ImageInfoDto infoDto =null;
        if (image != null) {
            infoDto = imageMapper.toLocalDto(image, post.getImageInfo(), UPLOAD_DIR);
        }
        User result = service.postUser(user, infoDto);
        UserIdResponseDto response = userMapper.toDto(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.CREATED);
        URI location = UriCreator.createUri("/api/users/{userId}", response.getUserId());

        return ResponseEntity.created(location).body(responseDto);
    }

    //어드민은 절대자의 권한이 존재한다. -|> 절대자의 권한을 사용하자.
    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(@PathVariable Long userId,
                                    @RequestPart UserPatchRequestDto patch,
                                    @RequestPart(required = false) MultipartFile image,
                                    @AuthenticationPrincipal Object principal){
        CheckLoginUser.validUserSame(principal,userId);
        User request = userMapper.toEntity(patch);
        request.setId(userId);
        ImageInfoDto infoDto = null;
        if(image != null){
            infoDto = imageMapper.toLocalDto(image, patch.getImageInfo(), UPLOAD_DIR);
        }
        User result = service.patchUser(request, infoDto);

        UserIdResponseDto response = userMapper.toDto(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.CREATED);
        URI location = UriCreator.createUri("/api/users/{userId}", response.getUserId());

        return ResponseEntity.ok().header("Location",location.toString()).body(responseDto);
    }

    //관리자 or 당사자만 볼 수 있음
    @GetMapping("/{userId}")
    public ResponseEntity getUser(@PathVariable Long userId,
                                  @AuthenticationPrincipal Object principal){
        CheckLoginUser.validUserSame(principal,userId);
        User result = service.getUser(userId);
        UserGetResponseDto response = userMapper.toGetDtoResponse(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity getUserPage(@AuthenticationPrincipal Object principal,
                                      Pageable pageable){
        CheckLoginUser.validAdmin(principal);
        Page<User> result = service.getUserPage(pageable);
        Page<UserGetResponseDto> response = userMapper.toGetDtoResponse(result);
        var responseDto = ResponseDto.builder().data(response).customCode(ResponseCode.OK).build();

        return ResponseEntity.ok(responseDto);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId,
                                     @AuthenticationPrincipal Object principal){
        CheckLoginUser.validUserSame(principal,userId);
        service.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }

}