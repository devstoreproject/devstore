package project.main.webstore.domain.users.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.users.dto.*;
import project.main.webstore.domain.users.entity.User;

@Component
public class UserMapper {
    public User toEntity(UserPostRequestDto post) {
        return new User(post);
    }

    public User toEntity(UserPatchRequestDto patch,Long userId) {
        if(patch != null)
            return new User(patch);
        return new User(userId);
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

    public User toEntity(UserGetPasswordRequestDto dto) {
        return new User(dto);
    }

    public UserGetPasswordResponseDto toGetPasswordResponse(User user) {
        return new UserGetPasswordResponseDto(user.getPassword());
    }
}
