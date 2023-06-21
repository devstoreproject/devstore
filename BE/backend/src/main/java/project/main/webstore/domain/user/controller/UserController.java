package project.main.webstore.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.main.webstore.domain.user.dto.UserPatchDto;
import project.main.webstore.domain.user.dto.UserPostDto;
import project.main.webstore.domain.user.dto.UserResponseDto;
import project.main.webstore.domain.user.entity.User;
import project.main.webstore.domain.user.mapper.UserMapper;
import project.main.webstore.domain.user.service.UserService;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final static String USER_DEFAULT_URL = "/user";
    private final UserService userService;
    private final UserMapper mapper;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserPostDto userPostDto) throws MessagingException {
        User user = userService.createUser(mapper.userPostToUser(userPostDto));

        return ResponseEntity.created(URI.create(USER_DEFAULT_URL + user.getId())).build();
    }

    @GetMapping("/confirm-email")
    public String verifyAccount(@Valid @RequestParam String token) {
        userService.confirmEmail(token);

        return "이메일 인증이 완료되었습니다.";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable @Valid Long id) {
        User user =  userService.getUser(id);
        UserResponseDto response = mapper.userToUserResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable @Positive Long id,
                                        @Valid @RequestBody UserPatchDto userPatchDto) {
        userPatchDto.setId(id);
        User user = userService.updateUser(mapper.userPatchToUser(userPatchDto));
        UserResponseDto response = mapper.userToUserResponse(user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable @Valid Long id) {
        userService.deleteUser(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
