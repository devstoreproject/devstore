package project.main.webstore.domain.users.mapper;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.DefaultMapper;
import project.main.webstore.domain.users.dto.UserGetPasswordRequestDto;
import project.main.webstore.domain.users.dto.UserGetPasswordResponseDto;
import project.main.webstore.domain.users.dto.UserGetResponseDto;
import project.main.webstore.domain.users.dto.UserIdResponseDto;
import project.main.webstore.domain.users.dto.UserPatchRequestDto;
import project.main.webstore.domain.users.dto.UserPostRequestDto;
import project.main.webstore.domain.users.entity.User;
import project.main.webstore.dto.CustomPage;

@Component
public class UserMapper implements DefaultMapper {
    public User toEntity(UserPostRequestDto post) {
        return new User(post);
    }

    public User toEntity(UserPatchRequestDto patch, Long userId) {
        if(patch != null) {
            return new User(patch, userId);
        }
        return new User(userId);
    }

    public UserIdResponseDto toDto(User user) {
        return new UserIdResponseDto(user.getId());
    }
    public UserGetResponseDto toGetDtoResponse(User user) {
        if(user == null)
            return null;
        return new UserGetResponseDto(user);
    }

    public CustomPage<UserGetResponseDto> toGetDtoResponse(Page<User> userPage) {
        Page<UserGetResponseDto> map = userPage.map(UserGetResponseDto::new);
        return transCustomPage(map);
    }

    public User toEntity(UserGetPasswordRequestDto dto) {
        return new User(dto);
    }

    public UserGetPasswordResponseDto toGetPasswordResponse(String password) {
        return new UserGetPasswordResponseDto(password);
    }
}
