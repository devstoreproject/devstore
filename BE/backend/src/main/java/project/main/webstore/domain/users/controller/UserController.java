package project.main.webstore.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.domain.users.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService service;
    @PostMapping("/users")
    public void postUser(@RequestBody UserPostRequestDto post) {
        User user = new User(post);
        service.postUser(user,null);

    }
}
