package project.main.webstore.domain.user.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import project.main.webstore.domain.user.dto.UserPatchDto;
import project.main.webstore.domain.user.dto.UserPostDto;
import project.main.webstore.domain.user.dto.UserResponseDto;
import project.main.webstore.domain.user.entity.User;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public User userPostToUser(UserPostDto userPostDto) {
        return User.builder()
                .email(userPostDto.getEmail())
                .password(userPostDto.getPassword())
                .nickName(userPostDto.getNickname())
                .build();
    }

    public User userPatchToUser(UserPatchDto userPatchDto) {
        return User.builder()
                .id(userPatchDto.getId())
                .nickName(userPatchDto.getNickname())
                .profileImage(userPatchDto.getProfileImage())
                .build();
    }

    public UserResponseDto userToUserResponse(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickName(user.getNickName())
                .profileImage(user.getProfileImage())
                .lastConnectedDate(user.getLastConnectedDate())
                .mileage(user.getMileage())
                .grade(user.getGrade())
                .userRole(user.getUserRole())
                .build();
    }
}
