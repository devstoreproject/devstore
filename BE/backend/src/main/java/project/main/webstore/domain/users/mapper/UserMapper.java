package project.main.webstore.domain.users.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.users.dto.UserGetResponseDto;
import project.main.webstore.domain.users.dto.UserIdResponseDto;
import project.main.webstore.domain.users.dto.UserPatchRequestDto;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.domain.users.entity.User;

@Component
public class UserMapper {
    public User toEntity(UserPostRequestDto post) {
        return User.jwtBuilder().post(post).build();
    }

    public User toEntity(UserPatchRequestDto patch) {
        return User.patchBuilder().patch(patch).build();
    }
    public UserIdResponseDto toDto(User user) {
        return new UserIdResponseDto(user.getId());
    }
    public UserGetResponseDto toGetDtoResponse(User user){
        return new UserGetResponseDto(user);
    }

    public Page<UserGetResponseDto> toGetDtoResponse(Page<User> userPage) {
        return userPage.map(UserGetResponseDto::new);
    }
}
